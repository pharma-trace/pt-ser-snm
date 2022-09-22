FROM public.ecr.aws/amazoncorretto/amazoncorretto:11-al2-jdk

COPY *.jar /app/app.jar
COPY startup.sh /app/startup.sh
RUN chmod +x /app/startup.sh

CMD [ "/app/startup.sh" ]