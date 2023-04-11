FROM python:3.9-alpine
ADD requirements.txt /
RUN pip install -r requirements.txt
ADD sensor.py /
ADD utils.py /
CMD [ "python", "./sensor.py" ]