package io.pharmatrace.ptsersnm.usecases;

import io.pharmatrace.ptsersnm.model.SerialNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Logger;

@Service
public class SerialNumberGeneratorService {

    static SecureRandom secureRandomStrong;

    @Autowired
    private SerialNumberService serialNumberService;


    public static final long BASE_PRIME = 1125899906842597L;

    public SerialNumberGeneratorService() {

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

    public List<SerialNumber> generateNumbers(
            final String alphaNumericString,
            final boolean useSecureRandom,
            final long M,
            final int N)
    {
        final long        startTime     = System.currentTimeMillis();
        final Set<String> serialNumbers = new HashSet<>();           // use Set instead of List as an easy protection
        List<SerialNumber> sList = new ArrayList<>();
        // from duplicate entries

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

//            final boolean snCouldAlreadyExist = GlobalSerialNumberReopository.hashExists(hashToCheck);
            final boolean snAlreadyExists = serialNumberService.existById(hash(sb.toString())).share().block();
            if(snAlreadyExists){
                continue;
            }


            SerialNumber temp= new SerialNumber();
            temp.setSerialNumber(sb.toString());

            sList.add(temp);

            serialNumbers.add(sb.toString());
        }
        System.out.println("total in list : "+serialNumbers.size());

        Logger.getGlobal()
                .info(String.format("Created %d serial numbers %s in %d ms.",
                        M, useSecureRandom ? "using SecureRandom" : "using Math.random",
                        System.currentTimeMillis() - startTime));

        return sList;

    }

    public List<SerialNumber> generateNumbers2(
            final String alphaNumericString,
            final boolean useSecureRandom,
            final long M,
            final int N)
    {

        Flux<SerialNumber> tempList = serialNumberService.findAll();
        Set<Long> hashes= new HashSet<>();
        tempList.flatMap(entity->{
            hashes.add(entity.getId());
            return null;
        });




        final long        startTime     = System.currentTimeMillis();
        final Set<String> serialNumbers = new HashSet<>();           // use Set instead of List as an easy protection
        List<SerialNumber> sList = new ArrayList<>();
        // from duplicate entries

//        final boolean snAlreadyExists = serialNumberService.findAll();
//        if(snAlreadyExists){
//            continue;
//        }
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

//            final boolean snCouldAlreadyExist = GlobalSerialNumberReopository.hashExists(hashToCheck);

            if(hashes.contains(hash(sb.toString()))){
                System.out.println("-----------same hash");
                continue;
            }


            SerialNumber temp= new SerialNumber();
            temp.setSerialNumber(sb.toString());

            sList.add(temp);

            serialNumbers.add(sb.toString());
        }
        System.out.println("total in list : "+serialNumbers.size());

        Logger.getGlobal()
                .info(String.format("Created %d serial numbers %s in %d ms.",
                        M, useSecureRandom ? "using SecureRandom" : "using Math.random",
                        System.currentTimeMillis() - startTime));

        return sList;

    }

    public static long hash(final String string)
    {
        long BASE_PRIME = 1125899906842597L;
        long      h   = BASE_PRIME;
        final int len = string.length();

        for(int i = 0; i < len; i++)
        {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }



}
