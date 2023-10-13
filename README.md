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

2. Connect the DHT11 sensor to the Raspberry Pi using a breadboard and SIM&NAT wires.

   - Connect the Signal Pin to GPIO pin 4 of the Pi.
   - Connect the Negative pin of the sensor to GPIO pin 6 of the Pi.
   - Connect the Vcc pin of the sensor to GPIO pin 2 of the Pi.

   ![Hardware Setup](https://camo.githubusercontent.com/cc42189988c7245bace156da1bf676673c9311d21c5274b8c90b5a96248aadf0/687474703a2f2f7777772e636972637569746261736963732e636f6d2f77702d636f6e74656e742f75706c6f6164732f323031352f31322f486f772d746f2d53657475702d7468652d44485431312d6f6e2d7468652d5261737062657272792d50692d466f75722d70696e2d44485431312d576972696e672d4469616772616d2e706e67)

3. Use a 10K Ohm pull-up resistor connected between the Vcc and signal lines.

![Pins](https://camo.githubusercontent.com/d569a752e89ccc2590364a8c197b10a54a6211ff58187a6dd0c44109082dfcbb/68747470733a2f2f7777772e72617370626572727970692d7370792e636f2e756b2f77702d636f6e74656e742f75706c6f6164732f323031322f30362f5261737062657272792d50692d4750494f2d4c61796f75742d4d6f64656c2d422d506c75732d726f74617465642d32373030783930302e706e67)

### Step 2: Test the Connection

To check the connection, run the sample program:

```bash
sudo python testDHT11.py
```

The output should show temperature in degrees Celsius and humidity percentage readings in the console every 5 seconds iteratively. Please note that the actual GPIO pin numbers and sensor types may vary based on your specific setup. Adjust the information accordingly.

### Step 3: AWS IoT Configuration

1. Go to [AWS IoT Core Console](https://us-east-1.console.aws.amazon.com/iot/home?region=us-east-1#/home).

2. Select **Connect Device** and note the code on the page.

3. On your Raspberry Pi console, try to ping the AWS IoT endpoint:

    ```bash
    ping xxxxxxxxx.xxxxxx.amazonaws.com
    ```

    If you receive a signal back, your connection is successful. Otherwise, check your network settings.

4. Create a new thing:

    - Give it a name.
    - Choose **Linux**.
    - Choose **Python SDK**.

5. Download the connection kit, unzip it, and run the following commands:

    ```bash
    chmod +x start.sh
    ./start.sh
    ```

    You should see messages pop up on your AWS IoT setup page.

6. Next Step: Testing

### Step 4: Testing

1. Go to the `RPI-to-IOT` directory that you just cloned.

2. Open `DataToIot.py` and modify the AWS IoT configuration:

    ```python
    # AWS IoT Configuration
    useWebsocket = False
    host = "xxxxx.amazonaws.com"
    rootCAPath = "root-CA.pem"
    certificatePath = "xxxxx-certificate.pem.crt"
    privateKeyPath = "xxxxx-private.pem.key"
    Client_ID = "RaspberryPi"
    AWS_IOT_MY_THING_NAME = "Your Thing Name"
    ```

    - For the `host`, get it from [AWS IoT Console](https://us-east-1.console.aws.amazon.com/iot/home?region=us-east-1#/home) under settings, you will see Endpoint, which is your host.

    - Unzip and put the downloaded files from the connection kit under the `RPI-to-IOT` directory, and enter the path correctly for those certificates.

3. Change the Thing name to the name you gave to your thing. Don't change the Client ID.

4. Run the Python file:

    ```bash
    sudo python DataToIOT.py
    ```

    You should see the Raspberry Pi sending data and sleeping for 10 seconds.

5. In the AWS IoT Console, go to **MQTT Test Client**:

    - Enter the topic, which is in the `# Topic configuration` in the Python file:

    ```python
    # Topic configuration
    topic = "awsiot/dht22"
    delay_sec = 10
    sensor_id = 'DHT22_xxx'
    ```

    - Subscribe to the topic `awsiot/dht22`.

6. Every 10 seconds, you should see messages showing on the bottom, displaying temperatures, humidity, and sensor IDs.


### Step 5: Store Data in Database

1. In the AWS IoT Console, navigate to **Message Routing** and then **Rules**.

2. Select **Create Rule**:

    - Give it a name and description (e.g., "Saving data to database").
    - For SQL statement, use either:
        - `SELECT * FROM 'awsiot/dht22'` (to store all data)
        - `SELECT Temperature, Pi_timestamp, Humidity FROM 'awsiot/dht22'` (to store specific fields)
        - Adjust the SQL statement based on your data.

3. For Rule actions, choose **DynamoDB**.

4. Create a DynamoDB table:

    - Click **Create DynamoDB table**.
    - Give it a name.
    - For the partition key, use `Pi_timestamp`.
    - Optionally, add a sort key if needed.
    - Leave the default settings and click **Create table**.

5. After creating the table, go back to the IoT rule page.

6. Select your table:

    - For the partition key, enter the partition key you just created (e.g., `Pi_timestamp`).
    - For the value, put in `$Pi_timestamp`.
    - Enter the sort key if you added one.
    - Choose a role; if you don't have one, create a new role.

7. To create a new role and add permissions:

    - Create a new role if needed.
    - Add a policy to this role; give DynamoDB access.
    - Click **View** after creating your role.
    - Click **Add Permissions** > **Attach Policies**.
    - Add permissions to this role, like DynamoDB full access.

8. Review your settings and click **Create**.

Now, you have successfully set up a rule to save the data passed from the Raspberry Pi into DynamoDB.

### Step 6: Create Lambda Function for API

1. Go to the [AWS Lambda Console](https://us-east-1.console.aws.amazon.com/lambda/home?region=us-east-1#/functions) and click on **Functions**.

2. Create a new function:

    - Click **Create function**.
    - Give it a name.
    - Choose **Node.js 18.x** as the runtime.
    - For architecture, choose **x86_64**.
    - Click **Create function**.

3. In the function page, go to `index.mjs` and replace the code with:

```javascript
import { DynamoDBClient } from "@aws-sdk/client-dynamodb";
import {
  DynamoDBDocumentClient,
  ScanCommand,
} from "@aws-sdk/lib-dynamodb";

const client = new DynamoDBClient({});
const dynamo = DynamoDBDocumentClient.from(client);
const tableName = "TempHum";

export const handler = async (event, context) => {
  let body;
  let statusCode = 200;
  const headers = {
    "Content-Type": "application/json",
  };

  try {
    body = await dynamo.send(
      new ScanCommand({ TableName: tableName })
    );
    body = body.Items[0].payload;
  } catch (err) {
    statusCode = 400;
    body = err.message;
  } finally {
    body = JSON.stringify(body);
  }

  return {
    statusCode,
    body,
    headers,
  };
};
```

4. Configure permissions:
   
    - Go to the Lambda script page, click on **Configuration** > **Permissions**.
    - Click on the role name, then go to the role page.
    - Add DynamoDB permission to the role.
   **Note:** Permission changes may take up to 10 minutes to take effect. If you encounter a permission error after making changes, wait for a while and then test again.

5. Set up an API Gateway:
  
    - Go to the [AWS API Gateway Console](https://us-east-1.console.aws.amazon.com/apigateway/main/apis?region=us-east-1).
    - Click **Create API**.
    - Choose **HTTP API**.
    - Give it a name and click **Next**.
    - Configure routes later, click **Next**.
    - Leave the default settings, click **Next**.
    - Review your settings and click **Create**.

6. Configure API routes:
     - Choose the API you just created.
     - On the left sidebar, go to **Develop** > **Routes**.
     - Click **Create**.
     - Choose the method (e.g., GET) and give it a name (e.g., `/getdata`).
     - Click **Create**.

7. Attach Lambda integration:

     - Go back to the Routes page, click on the route you just created.
     - Click **Attach Integration**.
     - Click **Create and attach an integration**.
     - Choose **Lambda function** as the integration type.
     - Choose the Lambda function you just created.
     - Click **Create**.

8. Get the API endpoint:
     - You can find the API endpoint in the Lambda function overview, under the **Triggers** section.
     - Alternatively, go to the API Gateway page, select your API, and find the **Invoke URL** + the route you just created.

Now, you have successfully created an API to get data from the database using AWS Lambda and API Gateway, when you go to your API endpoint, you should see data as a JSON on your browser.


### Step 7: Code the Website

1. Clone the Spring Boot project:

```bash
git clone https://github.com/Fulwing/Weather.git
```
2. Open the project and navigate to
`src/main/java/com/fulwin/controller/IndexController.java`

3. Update the API endpoint:

```java
// Temperature Humidity Part
String WEATHER_API_URL = "https://9oc0mrwy7l.execute-api.us-east-1.amazonaws.com/getData"; // This is my API data that includes all climate data.
String apiUrl = "YOUR_API_ENDPOINT_FROM_AWS_API_GATEWAY"; // Put your API endpoint here.
```
- Use the provided API or register at [Visual Crossing Weather API](https://www.visualcrossing.com/).
- Choose API, language HTTP, output JSON, and select only current. Copy and paste the URL into `WEATHER_API_URL`.
- 1000 usage of API per day, use it wisely!

4. Run the Spring Boot project.
  
5. Open the browser and go to [http://localhost:8080/](http://localhost:8080/)

   - If you can't load, ensure your port is 8080 or go to the port you've set for Tomcat.

5. You should see temperature, humidity, or climate data on the webpage.

6. Customize the project:

   - Change pictures in `src/main/resources/static/images`.
   - Add more functions as needed.

Now, you have successfully set up the website to display temperature, humidity, or climate data from your weather station.

### Step 8: Deploy Website on Amazon EC2

1. Go to the [Amazon EC2 Console](https://us-east-1.console.aws.amazon.com/ec2/home?region=us-east-1#Home:).

2. Click **Launch Instance**:
   - Give it a name.
   - Choose Amazon Linux.
   - Select t2.micro for the instance type.
   - Create a new key pair (choose RSA and .pem). Save the key pair securely.

3. Click **Launch Instance** and wait for the instance to start.

4. Click on your EC2 instance ID, then go to **Security** > **Security Groups** > **Edit Inbound Rules**:
   - Ensure 80, 443, 22 are all `0.0.0.0/0`.
   - Add a custom TCP for your Tomcat port (e.g., 8080) and set it to `0.0.0.0/0`.

5. Go to your Spring Boot project, clean, and then package. Locate the JAR file (e.g., `TempHum-0.0.1-SNAPSHOT.jar`) in the `target` folder.

6. Download and install [WinSCP](https://winscp.net/eng/index.php), then open it. Start a new tab:
   - File protocol: SFTP.
   - Host name: Copy the Public IPv4 DNS from your EC2 instance.
   - Port number: 22.
   - User name: ec2-user.
   - Advanced > Authentication: Choose the `.pem` key pair and convert it.
   - Login.

7. Upload the JAR file to `/home/ec2-user` on the server.

8. Open Git Bash in the folder containing the `.pem` key file, or use the EC2 instance Connect in the AWS Console.

9. Connect to the server using SSH:
   ```bash
   ssh -i "your-key.pem" ec2-user@your-ec2-dns.compute-1.amazonaws.com
   ```
   - Answer yes when prompted.
  
    Or go to your Instance click `Connect` and leave everything in default then click `Connect` again
    
10. In the server terminal, run the JAR file in the background:
    ```bash
    sudo nohup java -jar TempHum-0.0.1-SNAPSHOT.jar &
    ```
    - Replace TempHum-0.0.1-SNAPSHOT.jar with your JAR file name.

11. The website is now running. Open your browser and go to your instance's public IPv4, xx.xx.xx.xxx:8080, change the port if you set it to a different one.

Now, you have successfully deployed your website on Amazon EC2!
