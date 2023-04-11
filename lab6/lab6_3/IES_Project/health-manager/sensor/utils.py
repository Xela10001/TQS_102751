import random

ALIVE = 0
DEAD = -1
DEFAULT_HEART_RATE = 0
DEFAULT_BLOOD_PRESSURE_SYSTOLIC = 0
DEFAULT_BLOOD_PRESSURE_DIASTOLIC = 0
DEFAULT_BODY_TEMPERATURE = 16
DEFAULT_OXYGEN_SATURATION = 0
DEFAULT_RESPIRATION_RATE = 0


class PatientsLocalRepository(dict):
    def alive(self):
        return [patient for patient in self.values() if patient.state == ALIVE]

    def sorted(self, reverse=False):
        return dict(sorted(self.items(), reverse=reverse))


class Patient:
    """
    States:
     0      -    default
    -1      -    dead
    """

    def __init__(self, _id, first_name=None, last_name=None, age=None, gender=None, state=ALIVE, attrs=None):
        self.id = _id
        self.name = None if (first_name is None or last_name is None) else " ".join((first_name, last_name))
        self.age = age
        self.gender = gender
        self.state = state
        self.attrs = attrs if attrs else {}

    def kill(self):
        self.state = DEAD

    def revive(self):
        self.state = ALIVE

    def generateNewHeartRate(self, random_gen=False):
        heartRate = self.attrs.get('heartRate', DEFAULT_HEART_RATE)

        heartRateDeviance = 12
        heartRateLow = 60
        heartRateHigh = 105

        if random_gen:
            newHeartRate = random.randint(37, 150)

        elif self.state == DEAD:
            newHeartRate = DEFAULT_HEART_RATE

        else:

            if heartRate < heartRateLow - heartRateDeviance:
                newHeartRate = heartRate + random.uniform(1, 2 * heartRateDeviance)

            else:
                newHeartRate = random.uniform(
                    max(heartRateLow - heartRateDeviance / 2, heartRate - heartRateDeviance),
                    min(heartRateHigh + heartRateDeviance / 2, heartRate + heartRateDeviance))

        self.attrs['heartRate'] = newHeartRate
        return round(newHeartRate)

    def generateNewBloodPressure(self, random_gen=False):
        bloodPressureSystolic = self.attrs.get('bloodPressureSystolic', DEFAULT_BLOOD_PRESSURE_SYSTOLIC)
        bloodPressureDiastolic = self.attrs.get('bloodPressureDiastolic', DEFAULT_BLOOD_PRESSURE_DIASTOLIC)

        bloodPressureSystolicDeviance = 12
        bloodPressureSystolicLow = 90
        bloodPressureSystolicHigh = 120

        bloodPressureDiastolicDeviance = 8
        bloodPressureDiastolicLow = 60
        bloodPressureDiastolicHigh = 80

        if random_gen:
            newBloodPressureSystolic, newBloodPressureDiastolic = random.randint(98, 142), random.randint(62, 90)

        elif self.state == DEAD:
            newBloodPressureSystolic, newBloodPressureDiastolic = DEFAULT_BLOOD_PRESSURE_SYSTOLIC, DEFAULT_BLOOD_PRESSURE_DIASTOLIC

        else:

            if bloodPressureSystolic < bloodPressureSystolicLow - bloodPressureSystolicDeviance:
                newBloodPressureSystolic = bloodPressureSystolic + random.uniform(1, 2 * bloodPressureSystolicDeviance)

            else:
                newBloodPressureSystolic = random.uniform(
                    max(bloodPressureSystolicLow - bloodPressureSystolicDeviance / 2,
                        bloodPressureSystolic - bloodPressureSystolicDeviance),
                    min(bloodPressureSystolicHigh + bloodPressureSystolicDeviance / 2,
                        bloodPressureSystolic + bloodPressureSystolicDeviance))

            if bloodPressureDiastolic < bloodPressureDiastolicLow - bloodPressureDiastolicDeviance:
                newBloodPressureDiastolic = bloodPressureDiastolic + random.uniform(1,
                                                                                    2 * bloodPressureDiastolicDeviance)

            else:
                newBloodPressureDiastolic = random.uniform(
                    max(bloodPressureDiastolicLow - bloodPressureDiastolicDeviance / 2,
                        bloodPressureDiastolic - bloodPressureDiastolicDeviance),
                    min(bloodPressureDiastolicHigh + bloodPressureDiastolicDeviance / 2,
                        bloodPressureDiastolic + bloodPressureDiastolicDeviance))

        self.attrs['bloodPressureSystolic'] = newBloodPressureSystolic
        self.attrs['bloodPressureDiastolic'] = newBloodPressureDiastolic
        return round(newBloodPressureSystolic), round(newBloodPressureDiastolic)

    def generateNewBodyTemperature(self, random_gen=False):
        temperature = self.attrs.get('bodyTemperature', DEFAULT_BODY_TEMPERATURE)

        temperatureDeviance = 0.00001
        temperatureLow = 36
        temperatureHigh = 38

        if random_gen:
            newTemperature = random.uniform(35.1, 37.9)

        elif self.state == DEAD:
            newTemperature = max(DEFAULT_BODY_TEMPERATURE, temperature - 40 * temperatureDeviance)

        else:
            if temperature < temperatureLow - 50000 * temperatureDeviance:
                newTemperature = temperature + random.uniform(0, 2 * 50000 * temperatureDeviance)

            else:
                newTemperature = random.uniform(
                    max(temperatureLow - temperatureDeviance / 2, temperature - temperatureDeviance),
                    min(temperatureHigh + temperatureDeviance / 2, temperature + temperatureDeviance))

        self.attrs['bodyTemperature'] = newTemperature
        return round(newTemperature, 1)

    def generateNewRespirationRate(self, random_gen=False):
        respirationRate = self.attrs.get('respirationRate', DEFAULT_RESPIRATION_RATE)

        respirationRateDeviance = 0.5
        respirationRateLow = 12
        respirationRateHigh = 25

        if random_gen:
            newRespirationRate = random.randint(7, 28)

        elif self.state == DEAD:
            newRespirationRate = DEFAULT_RESPIRATION_RATE

        else:
            if respirationRate < respirationRateLow - 10 * respirationRateDeviance:
                newRespirationRate = respirationRate + random.uniform(0, 2 * 10 * respirationRateDeviance)

            else:
                newRespirationRate = random.uniform(
                    max(respirationRateLow - respirationRateDeviance / 2, respirationRate - respirationRateDeviance),
                    min(respirationRateHigh + respirationRateDeviance / 2, respirationRate + respirationRateDeviance))

        self.attrs['respirationRate'] = newRespirationRate
        return round(newRespirationRate)

    def generateNewOxygenSaturation(self, random_gen=False):
        oxygen = self.attrs.get('oxygenSaturation', DEFAULT_OXYGEN_SATURATION)

        oxygenDeviance = 0.00001
        oxygenLow = 0.95
        oxygenHigh = 1.00

        if random_gen:
            newOxygen = random.uniform(0.895, 1)

        elif self.state == DEAD:
            newOxygen = max(DEFAULT_OXYGEN_SATURATION, oxygen - 2 * 10 * oxygenDeviance)

        else:
            if oxygen < oxygenLow - 5000 * oxygenDeviance:
                newOxygen = oxygen + random.uniform(0, 2 * 5000 * oxygenDeviance)

            else:
                newOxygen = random.uniform(
                    max(oxygenLow - oxygenDeviance / 2, oxygen - oxygenDeviance),
                    min(oxygenHigh, oxygen + oxygenDeviance))

        self.attrs['oxygenSaturation'] = newOxygen
        return round(newOxygen, 2)

    def __str__(self):
        return "Patient({id}, {state}, {name}, {age}, {attrs})".format(
            id=self.id, state=self.state, name=self.name, age=self.age, gender=self.gender, attrs=self.attrs)

    def __repr__(self):
        return str(self)

    def __eq__(self, other):
        if isinstance(other, Patient) and other.id == self.id:
            return True
        return False


class Holder(object):
    def __init__(self):
        self.value = None

    def set(self, value):
        self.value = value
        return value

    def get(self):
        return self.value


QUEUE_PATIENT_DATA = "patients-queue"
EXCHANGE_PATIENT_DATA = "patients-exchange"
QUEUE_DEAD_PATIENT_DATA = "dead-patients-queue"
