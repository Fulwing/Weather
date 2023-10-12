# Weather Station Project

## Overview

This project is a step-by-step guide to help you build your own weather station and upload the data to a website. Whether you're a weather enthusiast, a student working on a project, or just curious, this guide will walk you through the process.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Customizable Sensors:** Choose the sensors that fit your needs, such as temperature, humidity, and atmospheric pressure.

- **Data Logging:** Collect and store weather data over time.

- **Web Upload:** Share your weather data on a website for easy access.

## Requirements

To build the weather station, you'll need the following hardware and software:

- Hardware:
  - Raspberry Pi 3 or higher
  - HDMI Cable
  - Keyboard
  - Mouse
  - Monitor
  - Breadboard
  - SIM&NAT Male to Female (assuming it's a specific cable)
  - DHT22 Sensor
  - Micro SD Card

- Software:

### Step 1: Set up your Raspberry Pi

1. Connect your Raspberry Pi to the keyboard, mouse, and monitor.

2. Open Raspberry Pi and follow the [setup](https://projects.raspberrypi.org/en/projects/raspberry-pi-setting-up/4)

### Step 2: Install Python

```bash
sudo apt-get update
sudo apt-get install python3
```

### Step 3: Get Adafruit Python DHT Sensor Library

```bash
sudo apt-get update
sudo apt-get install python3-pip
sudo python3 -m pip install --upgrade pip setuptools wheel
```

-Install with pip

```bash
sudo pip3 install Adafruit_DHT
```

-Compile and install from the repository

```bash
git clone https://github.com/adafruit/Adafruit_Python_DHT.git
cd Adafruit_Python_DHT
sudo apt-get update
sudo apt-get install build-essential python-dev python-openssl
sudo python setup.py install
```

### Step 4: Install AWS IoT Device SDK for Python

```bash
pip install AWSIoTPythonSDK
```

### Step 5: Clone the Git repository

```bash
git clone https://github.com/Fulwing/RPI-to-IOT.git
```

Now, your Raspberry Pi is set up with Python installed, the Adafruit DHT Sensor Library, and the AWS IoT Device SDK.

## Installation

1. Go to [AWS IoT](https://aws.amazon.com/) to register an account for IoT uses.

### Step 1: Hardware Setup

1. Power up your Raspberry Pi.

2. Connect the DHT11 sensor to the Raspberry Pi using a breadboard and jumper wires.

   - Connect the Signal Pin to GPIO pin 4 of the Pi.
   - Connect the Negative pin of the sensor to GPIO pin 6 of the Pi.
   - Connect the Vcc pin of the sensor to GPIO pin 2 of the Pi.

   ![Hardware Setup](https://camo.githubusercontent.com/cc42189988c7245bace156da1bf676673c9311d21c5274b8c90b5a96248aadf0/687474703a2f2f7777772e636972637569746261736963732e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031352f31322f486f772d746f2d53657475702d7468652d44485431312d6f6e2d7468652d5261737062657272792d50692d466f75722d70696e2d44485431312d576972696e672d4469616772616d2e706e67)

3. Use a 10K Ohm pull-up resistor connected between the Vcc and signal lines.

### Step 8: Test the Connection

To check the connection, run the sample program:

```bash
sudo python testDHT11.py
```

The output should show temperature in degrees Celsius and humidity percentage readings in the console every 5 seconds iteratively. Please note that the actual GPIO pin numbers and sensor types may vary based on your specific setup. Adjust the information accordingly.

