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

2. Choose the Raspberry Pi OS during the setup process.

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

Now, your Raspberry Pi is set up with Python installed, the Adafruit DHT Sensor Library, and the AWS IoT Device SDK. You could run Test.py to test if sensor is working, you could get the temperature and humidity printed out

## Installation
