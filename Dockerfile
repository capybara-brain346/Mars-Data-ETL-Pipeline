FROM python:3.10

RUN pip install 'apache-airflow==2.10.2' --constraint "https://raw.githubusercontent.com/apache/airflow/constraints-2.10.2/constraints-3.10.txt"

COPY Airflow/airflow.py /airflow.py

RUN airflow db init

EXPOSE 8080

CMD ["airflow","webserver"]

