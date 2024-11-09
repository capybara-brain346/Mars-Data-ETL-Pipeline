FROM openjdk:21-slim

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    ln -s /usr/bin/python3 /usr/bin/python && \
    ln -s /usr/bin/pip3 /usr/bin/pip

RUN pip install apache-airflow

RUN pip install azure-storage-blob==12.8.1

COPY dags /usr/local/airflow/dags

ENV AIRFLOW_HOME=/usr/local/airflow

RUN pip install -r $AIRFLOW_HOME/dags/requirements.txt

COPY extract/Extract-1.0-SNAPSHOT.jar /usr/local/airflow/file.jar

ENTRYPOINT ["airflow"]

CMD ["scheduler"]
