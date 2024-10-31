from datetime import datetime
from airflow import DAG
from airflow.operators.bash import BashOperator

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2024, 10, 31),
    'retries': 1,
}

dag = DAG(
    'run_java_jar',
    default_args=default_args,
    schedule_interval='@daily',  
)

run_java_jar = BashOperator(
    task_id='run_java_jar_task',
    bash_command='java -jar /path/to/your/file.jar',
    dag=dag,
)
