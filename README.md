# Sahaayak

Your learning partner  

OpenVidu used for supporting video calls.  
Firebase is powering the backend.  
NodeJS code for push notifications is not in this repository currently.  

#### Setting up OpenVidu
Ensure you've generated your debug keys. If you're unsure on how to do that, `openvidus_extras/script.sh` has the steps commented.  

Set-up docker  
```bash
sudo apt-get install docker.io
```

Navigate to the folder openvidu_extras. Run script.sh with  
```bash
./script.sh
```

This will setup a server on your machine.  

#### Referring to OpenVidu server
Best way is to setup a URL key-value entry in the root of your firebase database to allow dynamic changes in the IP. However, if you still want to do it with a static IP, here are the steps:  

- In the strings.xml file, replace the IP Address with your machine's IP Address.  
  For example,  
  ```xml
   <string name="default_openvidu_url">https://192.168.0.8:4443/</string>
  ```
- Make sure to replace the code for fetching URL dynamically inside the app for using the above resource string.  

192.168.0.8 is your system's IP Address.

Build the app and run it.

