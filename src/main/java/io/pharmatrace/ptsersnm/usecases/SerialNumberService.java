package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.context.orm.SerialNumberRepository;
import io.pharmatrace.ptsersnm.exceptions.ApiRequestException;
import io.pharmatrace.ptsersnm.model.SerialNumber;

import io.pharmatrace.ptsersnm.model.SnProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;

@Lazy
@Service
public class SerialNumberService implements CRUDQBService<SerialNumber, Long>{

    @Autowired
    SerialNumberRepository serialNumberRepository;

    @Autowired
    SnProfileService snProfileService;

    public static final long BASE_PRIME = 1125899906842597L;
    static SecureRandom secureRandomStrong;

    public SerialNumberService() {
        try
        {
            this.secureRandomStrong
                    = SecureRandom.getInstance("SHA1PRNG");
        }
        catch(final NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            this.secureRandomStrong = null;
        }
    }


    @Override
    public R2dbcRepository<SerialNumber, Long> getRepository() {
        return serialNumberRepository;
    }


    @Override
    public Mono<SerialNumber> create(Mono<SerialNumber> businessObj) {

        return businessObj.flatMap(entity ->{
            return CRUDQBService.super.create(Mono.just(entity));
        });

    }

    public Flux<SerialNumber> saveAll(Flux<SerialNumber> entities){
        long startTime     = System.currentTimeMillis();
        Flux<SerialNumber> list = serialNumberRepository.saveAll(entities);
        Logger.getGlobal()
                .info(String.format("persisted serial numbers in %d ms.",
                        System.currentTimeMillis() - startTime));

        return list;
    }


    public Mono<Boolean> existById(long id){
        return serialNumberRepository.existsById(id);
    }

    public Flux<SerialNumber> findAll(){
        return serialNumberRepository.findAll();
    }

    public Flux<SerialNumber> getSerialNumbers(UUID profileId, int requestSize){

        SnProfile snProfile= snProfileService.getProfileById(profileId).share().block();

        int availableSerialNumbers = serialNumberRepository.countSerials(profileId).share().block();
        if(snProfile.getMaxRequestSize()<requestSize){
            throw new ApiRequestException("Requested size of serial numbers exceeds the maximum allowed size ("+snProfile.getMaxRequestSize()+").");
        }else if (availableSerialNumbers<requestSize){
            generateNumbers(profileId, true).doOnComplete(()->{
            throw new ApiRequestException("Sorry, the server is bussy. Please try again later.");
            });
        }


        long usedIndex = snProfile.getSerialNumberUsedIndex();
        Flux<SerialNumber> serialNumberFlux = serialNumberRepository.findAllByStatus(profileId, "AVAILABLE", requestSize).doOnComplete(()->{
            serialNumberRepository.udateStatus(profileId, usedIndex, usedIndex+requestSize,"USED").subscribe(System.out::println);
        });

        snProfile.setSerialNumberUsedIndex(usedIndex+requestSize);
        snProfileService.update(Mono.just(snProfile)).subscribe(System.out::println);
        generateNumbers(profileId, true).subscribe(System.out::println);
        return serialNumberFlux;
    }

    public Flux<SerialNumber> generateNumbers(
            UUID profileId,
            final boolean useSecureRandom)
    {

        SnProfile serialNumberProfile = snProfileService.getProfileById(profileId).share().block();
        final String alphaNumericString = serialNumberProfile.getSerialNumChars();
        final long M = (long)(serialNumberProfile.getMaxRequestSize()*1.2);
        final int N = 20;
        long serialNumbereindex= serialNumberProfile.getSerialNumberIndex();

        final long        startTime     = System.currentTimeMillis();
        final Set<String> serialNumbers = new HashSet<>();
        List<SerialNumber> sList = new ArrayList<>();

        while(serialNumbers.size() < M)
        {
            final StringBuilder sb = new StringBuilder(N);
            for(int i = 0; i < N; i++)
            {
                final double myRandom =
                        useSecureRandom ? secureRandomStrong.nextDouble() : Math.random();
                // generate a random number between
                // 0 to AlphaNumericString variable length
                final int index = (int)(alphaNumericString.length() * myRandom);

                // add Character one by one in end of sb
                sb.append(alphaNumericString
                        .charAt(index));
            }

            SerialNumber serialNumber= new SerialNumber();
            serialNumber.setSerialNumber(sb.toString());
            serialNumber.setProfileId(profileId);
            serialNumber.setStatus("AVAILABLE");
            serialNumber.setSnIndex(++serialNumbereindex);

            sList.add(serialNumber);
            serialNumbers.add(sb.toString());
        }
        serialNumberProfile.setSerialNumberIndex(serialNumbereindex);
        snProfileService.update(Mono.just(serialNumberProfile)).subscribe(System.out::println);

        return this.saveAll(Flux.fromIterable(sList));

    }


}
