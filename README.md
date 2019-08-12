# AndroidApp
> Getting hands dirty with Android Application Development

## Features
### Charts using MPAndroidChart
### App Links
### AndroidX Migration using Studio
### Firebase Integration
 >#### Push Notification

  ```
  Pushing Notification to specific device
  
  curl -X POST \
  https://fcm.googleapis.com/fcm/send \
  -H 'Authorization: key=<Your:Server:Key>' \
  -H 'Content-Type: application/json' \
  -d '{
    "to": "<Your:App:ID>",
    "data": {
        "title": "Alert",
        "summary": "Alert Summary",
        "details": "This is alert\nThis is alert\nThis is alert "
    }
  }'

  Where,
  <Your:Server:Key> can be found on Firbase console <br /> 
  <Your:App:ID> app specific id
  ```
>####  Topic Subscription
  
  ```
  Pushing Notification to Subscribed devices
  
  curl -X POST \
  https://fcm.googleapis.com/fcm/send \
  -H 'Authorization: key=<Your:Server:Key>' \
  -H 'Content-Type: application/json' \
  -d '{
    "to": "<Your:Topic:Name>",
    "data": {
        "title": "Alert",
        "summary": "Alert Summary",
        "details": "This is alert\nThis is alert\nThis is alert "
    }
  }'

  Where,
  <Your:Server:Key> can be found on Firbase console <br /> 
  <Your:Topic:Name> Name of topic when message will be pulished
  ```

### Custom View Pager
### Splash Screens with Animation
  >* Evrytime
  >* Onetime
### Drawer/Hamburger Menu
### Room DB
### Work Manager and Async Tasks
  >* Ontime
  >* Scheduled(Unique)
