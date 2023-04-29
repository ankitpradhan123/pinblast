# pinblast
Pinblast is an advanced pin scheduling app built on the top of the pinterest API that overcomes the cons of inbuilt pinterest pin scheduling feature.

The cons of using default pinterest pin scheduler are:

* You can only schedule up to 2 weeks in advance.
* You can only schedule one Pin at a time.
* You can’t schedule more than 100 pins ahead.
* Once they’re scheduled you can’t edit them (but you can delete them!)


The problems that were solved:

* There will be no limtations in the time frame for scheduling pins.
* You can schedule more than 100 pins.

## Requirements

1. Java - 11

2. Maven - 3.x.x

3. MySQL - 5.x.x

## Getting Started

**1. Clone the application**

```bash
git clone https://github.com/ankitpradhan123/pinblast.git
```
**2. Create MySQL database**

```bash
create database db_pinterest
```
**3. Create Qartz tables**

The project stores all the scheduled Jobs in MySQL database. You'll need to create the tables that Quartz uses to store Jobs and other job-related data. Please create Quartz specific tables by executing the `quartz_tables.sql` script located inside `src/main/resources` directory.

**4. Change MySQL username and password as per your MySQL installation**

open `src/main/resources/application.properties`, and change `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation

**5. Build and run the app using maven**

Finally, You can run the app by typing the following command from the root directory of the project -

```bash
mvn spring-boot:run
```

## Scheduling Pins

**1. Create a board**

```bash
curl --location 'http://localhost:8080/api/boards' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Test board 2",
    "description" : "This is the test board created from API -2",
    "privacy": "PUBLIC"
}'

# Response
{
    "id": "2",
    "name": "Test board 2",
    "description": "This is the test board created from API -2",
    "privacy": "PUBLIC",
    "board_id": "895090563379436404",
    "pin_count": 0
}
```

**2. Schedule a pin**

The `id` that you got in the response from step 1 will be used in the url.

```bash
curl --location 'http://localhost:8080/api/{id}/schedulePins' \
--header 'Content-Type: application/json' \
--data '{
    "dateTime" : "2023-04-30T00:23:00",
    "timeZone" : "Asia/Kolkata",
    "pin" : {
      "title": "My Pin 3",
      "description": "This pin was scheduled by quartz library for Test board 2",
      "media_source": {
        "source_type": "image_url",
        "url": "https://i.pinimg.com/564x/28/75/e9/2875e94f8055227e72d514b837adb271.jpg"
      }
  }
}'

# Response
{
    "success": true,
    "jobId": "41627358-923c-4105-b934-789446c6f2fa",
    "jobGroup": "pin-jobs",
    "message": "Pin Scheduled Successfully!"
}
```
