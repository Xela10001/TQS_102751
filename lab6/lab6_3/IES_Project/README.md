# IES Project 2022/2023

# Abstract

**Title:** Health Manager

**Description:** Manager web app that lets you easily check on and manage all your patients

**Features:**

    [Core] view any patient's details, updated in real time; 

    [Core] add, edit, remove a patient; 

    [Core] app calculates and displays a health state (e.g. "Unhealthy") based on patient's health details;

    filter and order patients.

# Team

Alexandre Gazur 102751 (Team Manager)

Daniel Ferreira 102885 (Architect)

Ricardo Pinto 103078 (DevOps master)

All 3 of us are both Product Owners and Developers

# Bookmarks

**Backlog** (mostly managed by Alexandre): https://grupo-ies.atlassian.net/jira/software/projects/I2/boards/2

# Deployment

In UA's network, 192.168.160.223:8080 or 10.139.0.1:8080

# API (JSON)

## /api/v1/patients

GET: returns array with all patients, each with the fields id, firstName, lastName, age and gender, ordered by id by default

POST: add a patient

**Optional parameters:**

<ins>healthData=none</ins> (default): returns only the fields id, firstName, lastName, age and gender, ordered by id by default

<ins>healthData=latest</ins>: adds the fields latestHealthState, latestHeartRate, latestBodyTemperature, latestBloodPressure, latestRespirationRate and latestOxygenSaturation

<ins>healthData=all</ins>: adds the fields/lists healthStates, heartRates, bodyTemperatures, bloodPressures, respirationRates and oxygenSaturations

<ins>sortBy={string}</ins> ("id" by default): sort patients by one or more fields (separated by comma ","), may prefix a field with "+" for ascending order (default) or "-" for descending order (for example *sortBy=-age,firstName* sorts by age descending then firstName ascending)

<ins>limit={int}</ins> (none by default): return only n patients

**Examples:**

*/api/v1/patients?sortBy=-age,id&limit=5* orders patients by age descending then id ascending, then returns first 5

*/api/v1/patients?healthData=latest* returns all patients along with their latest health data

## /api/v1/patients/{id}

GET: returns id, firstName, lastName, age and gender of patient with specified id

PUT and DELETE update it and delete it, respectively

**Optional parameters:**

<ins>healthData</ins> as explained before in /api/v1/patients

**Examples:**

*/api/v1/patients/1?healthData=all* returns basic information and all health data (6 lists, one for each health attribute) of patient id 1

## The following 12 pages are, by default, ordered by date time descending then id (of record) ascending, and share the same optional parameters:

<ins>sortBy={string}</ins> (*-dateTime,id* by default): sort records by one or more fields (separated by comma ","), may prefix a field with "+" for ascending order (default) or "-" for descending order (for example *sortBy=-heartRate,id* sorts the records by heart rate value descending, then id (of record) ascending)

<ins>limit={int}</ins> (none by default): return only x elements

<ins>startDateTime={YYYY:MM:DDTHH:MM:SS}</ins> (none by default): return only records after or at specified date and time

<ins>endDateTime={YYYY:MM:DDTHH:MM:SS}</ins> (none by default): return only records before or at specified end date and time

**Examples:**

*/api/v1/patients/1/heartRates?sortBy=-heartRate,dateTime&startDateTime=2022-12-01T00:00:00&limit=10* returns heart rate records of patient with id 1 after 2022-12-01 00:00:00, ordered by heart rate value descending then date time ascending, and limited to first 10

## The following 6 pages return a list with the specific health attribute (heart rate, body temperature, ...) records of all patients 

### /api/v1/patients/healthStates

Each health state record has a "state" field, which can be "Very healthy", "Healthy", "Neutral", "Unhealthy", "Very unhealthy"; these records are calculated once every minute by our app from the health attributes (heart rate, blood pressure, body temperature, respiration rate and oxygen saturation) closest to that time in a 5 minute range

Ordering by health state (sortBy=healthState) ascending returns "Very unhealthy" records first and "Very healthy" records last

### /api/v1/patients/heartRates

### /api/v1/patients/bodyTemperatures

### /api/v1/patients/bloodPressures

### /api/v1/patients/respirationRates

### /api/v1/patients/oxygenSaturations

## The following 6 pages return a list with the specific health attribute (heart rate, body temperature, ...) records of patient with specified id

### /api/v1/patients/{id}/healthStates

### /api/v1/patients/{id}/heartRates

### /api/v1/patients/{id}/bloodPressures

### /api/v1/patients/{id}/bodyTemperatures

### /api/v1/patients/{id}/respirationRates

### /api/v1/patients/{id}/oxygenSaturations
