import asyncio
import json
import re
import sys
from utils import *
import pika
import select
import requests
import datetime

HELP = """
------------------------------------------------- MENU -------------------------------------------------
1. (Q)uit:                                              - Terminar o processo
2. (H)elp:                                              - Mostrar este menu
3. (K)ill patient_id:                                   - Matar um paciente
4. (R)evive patient_id:                                 - Reviver um pacient
5. (I)ncrease patient_id attribute rate                 - Aumentar atributo de paciente por rate
6. (D)ecrease patient_id attribute rate                 - Diminuir atributo de paciente por rate
--------------------------------------------------------------------------------------------------------
"""

connection = pika.BlockingConnection(pika.ConnectionParameters('localhost', 5672))
channel = connection.channel()
channel.queue_declare(queue=QUEUE_PATIENT_DATA, durable=True, arguments={
    'x-message-ttl': 30000,
    'x-dead-letter-exchange': '',
    'x-dead-letter-routing-key': QUEUE_DEAD_PATIENT_DATA
})
channel.queue_declare(queue=QUEUE_DEAD_PATIENT_DATA, durable=True)
channel.exchange_declare(exchange=EXCHANGE_PATIENT_DATA, exchange_type='topic', durable=True)
channel.queue_bind(exchange=EXCHANGE_PATIENT_DATA, queue=QUEUE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA)

patientsLocalRepository = PatientsLocalRepository()
#eTag = None

def call_api():
    global patientsLocalRepository
    #global eTag

    host = "localhost"
    if len(sys.argv) > 1:
        host = sys.argv[1]

    url = 'http://' + host + ':8080/api/v1/patients?healthData=all'

    response = requests.head(url)
    #url_eTag = response.headers['ETag']

    #if eTag != url_eTag:

        #eTag = url_eTag

        #...

    response = requests.get(url)
    jsonResponse = response.json()

    patientsLocalRepository = PatientsLocalRepository()

    for obj in jsonResponse:
        _id = obj["id"]
        _fname = obj["firstName"]
        _lname = obj["lastName"]
        _age = obj["age"]
        _gender = obj["gender"]

        _heartrates = obj["heartRates"]
        _bodytemperatures = obj["bodyTemperatures"]
        _bloodpressures = obj["bloodPressures"]
        _respirationrates = obj["respirationRates"]
        _oxygensaturations = obj["oxygenSaturations"]

        _heartrate = _heartrates[-1]["heartRate"] if _heartrates else DEFAULT_HEART_RATE
        _bodytemperature = _bodytemperatures[-1]["bodyTemperature"] if _bodytemperatures else DEFAULT_BODY_TEMPERATURE
        _bloodpressuresystolic, _bloodpressurediastolic = (_bloodpressures[-1]["systolic"], _bloodpressures[-1]["diastolic"]) if _bloodpressures else (DEFAULT_BLOOD_PRESSURE_SYSTOLIC, DEFAULT_BLOOD_PRESSURE_DIASTOLIC)
        _respirationrate = _respirationrates[-1]["respirationRate"] if _respirationrates else DEFAULT_RESPIRATION_RATE
        _oxygensaturation = _oxygensaturations[-1]["oxygenSaturation"] if _oxygensaturations else DEFAULT_OXYGEN_SATURATION

        patientsLocalRepository[_id] = Patient(_id, _fname, _lname, _age, _gender, 0, {
            'bodyTemperature': _bodytemperature,
            'heartRate': _heartrate,
            'bloodPressureSystolic': _bloodpressuresystolic,
            'bloodPressureDiastolic': _bloodpressurediastolic,
            'respirationRate': _respirationrate,
            'oxygenSaturation': _oxygensaturation
        })

async def GenerateAndSendHeartRate():
    while True:
        for _id, patient in patientsLocalRepository.items():

            time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            newHeartRate = patient.generateNewHeartRate(random_gen=True)
            newHeartRateMessage = {'patientId': _id, 'dateTime': time, 'heartRate': newHeartRate}

            channel.basic_publish(exchange=EXCHANGE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA, body=json.dumps(newHeartRateMessage).encode('utf-8'))
        await asyncio.sleep(1)

async def GenerateAndSendBloodPressure():
    while True:
        for _id, patient in patientsLocalRepository.items():

            time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            newBloodPressureSystolic, newBloodPressureDiastolic = patient.generateNewBloodPressure(random_gen=True)
            newBloodPressureMessage = {'patientId': _id, 'dateTime': time,
                                       'bloodPressureSystolic': newBloodPressureSystolic,
                                       'bloodPressureDiastolic': newBloodPressureDiastolic}

            channel.basic_publish(exchange=EXCHANGE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA, body=json.dumps(newBloodPressureMessage).encode('utf-8'))
        await asyncio.sleep(10)

async def GenerateAndSendBodyTemperature():
    while True:
        for _id, patient in patientsLocalRepository.items():

            time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            newBodyTemperature = patient.generateNewBodyTemperature(random_gen=True)
            newBodyTemperatureMessage = {'patientId': _id, 'dateTime': time, 'bodyTemperature': newBodyTemperature}

            channel.basic_publish(exchange=EXCHANGE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA, body=json.dumps(newBodyTemperatureMessage).encode('utf-8'))
        await asyncio.sleep(30)


async def GenerateAndSendRespirationRate():
    while True:
        for _id, patient in patientsLocalRepository.items():

            time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            newRespirationRate = patient.generateNewRespirationRate(random_gen=True)
            newRespirationRateMessage = {'patientId': _id, 'dateTime': time, 'respirationRate': newRespirationRate}

            channel.basic_publish(exchange=EXCHANGE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA, body=json.dumps(newRespirationRateMessage).encode('utf-8'))
        await asyncio.sleep(10)


async def GenerateAndSendOxygenSaturation():
    while True:
        for _id, patient in patientsLocalRepository.items():

            time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
            newOxygenSaturation = patient.generateNewOxygenSaturation(random_gen=True)
            newOxygenSaturationMessage = {'patientId': _id, 'dateTime': time, 'oxygenSaturation': newOxygenSaturation}

            channel.basic_publish(exchange=EXCHANGE_PATIENT_DATA, routing_key=QUEUE_PATIENT_DATA, body=json.dumps(newOxygenSaturationMessage).encode('utf-8'))
        await asyncio.sleep(15)

async def SyncPatients():
    while True:
        call_api()
        await asyncio.sleep(1)

async def GenerateAndSend():
    task1 = asyncio.create_task(SyncPatients())
    task2 = asyncio.create_task(GenerateAndSendHeartRate())
    task3 = asyncio.create_task(GenerateAndSendBloodPressure())
    task4 = asyncio.create_task(GenerateAndSendBodyTemperature())
    task5 = asyncio.create_task(GenerateAndSendRespirationRate())
    task6 = asyncio.create_task(GenerateAndSendOxygenSaturation())
    return await asyncio.gather(task1, task2, task3, task4, task5, task6)

async def TakeInput():
    def menu():
        print(HELP)

    menu()
    h = Holder()

    isOP1 = re.compile("[ \t]*(HELP|H)[ \t]*", flags=re.IGNORECASE)
    isOP2 = re.compile("[ \t]*(QUIT|Q)[ \t]*", flags=re.IGNORECASE)
    isOP3 = re.compile("[ \t]*(KILL|K)[ \t]+([0-9]+)[ \t]*", flags=re.IGNORECASE)
    isOP4 = re.compile("[ \t]*(REVIVE|RV|R)[ \t]+([0-9]+)[ \t]*", flags=re.IGNORECASE)
    isOP5 = re.compile("[ \t]*(INCREASE|INC|I)[ \t]+([0-9]+)[ \t]+([a-zA-Z0-9]+)[ \t]+([0-9]+)[ \t]*",
                       flags=re.IGNORECASE)
    isOP6 = re.compile("[ \t]*(DECREASE|DEC|D)[ \t]+([0-9]+)[ \t]+([a-zA-Z0-9]+)[ \t]+([0-9]+)[ \t]*",
                       flags=re.IGNORECASE)

    while True:
        await asyncio.sleep(0.1)
        while sys.stdin in select.select([sys.stdin], [], [], 0)[0]:
            option = sys.stdin.readline().strip()

            if isOP1.fullmatch(option):
                print(HELP)

            elif isOP2.fullmatch(option):
                print("Exiting...")
                connection.close()
                sys.exit(0)

            elif h.set(isOP3.fullmatch(option)):
                patient_id = int(h.get().group(2))
                kill(patient_id)

            elif h.set(isOP4.fullmatch(option)):
                patient_id = int(h.get().group(2))
                revive(patient_id)

            elif h.set(isOP5.fullmatch(option)):
                patient_id = int(h.get().group(2))
                attribute = h.get().group(3)
                rate = int(h.get().group(4))
                increase(patient_id, attribute, rate)

            elif h.set(isOP6.fullmatch(option)):
                patient_id = int(h.get().group(2))
                attribute = h.get().group(3)
                rate = int(h.get().group(4))
                decrease(patient_id, attribute, rate)

            else:
                print("\033[91mInvalid Option!\033[0m")
                print(HELP)

            print()


# loop = asyncio.get_event_loop()
# loop.create_task(Task1())
# loop.create_task(Task2())
# loop.run_forever()

def kill(patient):
    patientsLocalRepository[patient].kill()


def revive(patient):
    patientsLocalRepository[patient].revive()


def increase(patient, attribute, rate):
    patientsLocalRepository[patient].increase(attribute, rate)


def decrease(patient, attribute, rate):
    patientsLocalRepository[patient].increase(attribute, rate)


async def main():
    task1 = asyncio.create_task(GenerateAndSend())
    task2 = asyncio.create_task(TakeInput())
    return await asyncio.gather(task1, task2, return_exceptions=True)


if __name__ == "__main__":
    print(asyncio.run(main()))
