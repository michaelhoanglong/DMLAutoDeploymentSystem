# Mobile Distributed Machine Learning Auto Deployment System
This is the implementation of Distributed Machine Learning Auto Deployment System.
The distributed machine learning training is executed on the Amazon Web Service EC2 instances (some of them will be parameter servers and some of the will be the workers).
The Model Serving Service which handle the user request to predict a specific test image on a specified trained model.

## I - Setting Up
	### A - Training Server
		#### 1. Set up environment to communicate with the Amazon Web Service via Command Line Interface (AWS CLI):
			##### - Set credentials in the AWS credentials profile file on your local system, located at:
				###### + Unix or Linux:
					~/.aws/credentials
				###### + Windows
					C:\Users\USERNAME \.aws\credentials
			##### - Set the AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY environment variables:
				###### + Unix or Linux: 
					export AWS_ACCESS_KEY_ID=your_access_key_id
					export AWS_SECRET_ACCESS_KEY=your_secret_access_key
				###### + Windows:
					set AWS_ACCESS_KEY_ID=your_access_key_id
					set AWS_SECRET_ACCESS_KEY=your_secret_access_key
			##### - Set the AWS Region at:
				###### + Unix or Linux:
					~/.aws/config
				###### + Windows
					C:\Users\USERNAME\.aws\config
			##### - Set the AWS_REGION environment variable:
				###### + Unix or Linux:
					export AWS_REGION=your_aws_region
				###### + Windows
					set AWS_REGION=your_aws_region
		#### 2. Go to AWSManager folder and import the code to set up Auto-deployment Server. This code uses Spring MVC 3.0 frameworks which is built on Marven:
			##### - Enter your AWS Credential into the [YOUR AWS Credential] field in AWSManager/InstanceStartUp.sh 
			##### - Set up Apache Tomcat Server and deploy the code.
			##### - Start the server.
	### B - Serving Server
		#### 1. Start an AWS EC2 instance (T2.micro with Ubuntu will be sufficient).
		#### 2. Run the ServingServerInstall.sh to set up the Serving Server
		#### 3. Go to myproject folder and start the gunicorn service:
			cd ~/myproject
			gunicorn --bind 0.0.0.0:1500 wsgi:app
	*Now both the machine learning training service and serving service are set up and ready for development.*

## II - Machine Learning Training Auto-deployment Server:
	### A - UI Componets: using JSP to develop:
		#### 1. Request Page:
			- This is where user will make their training requests by filling the form accordingly.
			- Data Preprocessing field is where users fill in their code to preprocess data before training (view "datapreprocessingsample.py").
			- Training Algorithm field is where users fill in their algorithm in this case is deep neural network (view "trainingalgorithmsample.py").
			- Additional Info field is where users fill in the helper functions defined by themselves to import and use in other parts like Data Preprocessing and Training Algorithm.
		#### 2. Data Page:
			- This is where users can view their model training status (initializing, training, completed).
			- Once the model training status is Completed, the download url will be available for download.
			- User can terminate their training at anytime. This will terminate the AWS Instances that users start for their model training.
		#### 3. Serving Page:
			- Users can put in their test image (in form of URL).
			- Specifying the image size (width x height x number of channels)
			- Specifying the train model url (the trained model MUST Be zipped)
	### B - Important Service components:
		#### 1. DBService: handle the works related to MySQL Database (Create, Read, Update, Delete)
		#### 2. CommandService: handle the works related to the AWS System Manager Agent (SSM) which send the command to the AWS EC2 instances in the command line format.
		#### 3. DeploymentService: handle the process of creating, starting and terminating the AWS EC2 instances.
		#### 4. TensorflowService: prepare the user request and their commands so that CommandService can start sending the commands later.
		#### 5. TensorflowServingClientService: handles the HTTP communication with the Model Serving Server (developed with python and hosted on Green Unicorn - gunicorn).

## III - Model Serving Server:
	### A - Codes:
		#### 1. The code for this is located at distributed-tensorflow/serving.
		#### 2. The Python Flask server implementation is in the myproject.py.
		#### 3. wsgi.py is used to run the hosted server for the Flask application.
	### B - Work flows:
		#### 1. Receiving the HTTP Request from Auto-deployment Server: 
			- Upon receiving the HTTP request from the auto-deployment server, this Flask Application will preprocess the images and the models, then save them in to the predefined directory in the server.
		#### 2. Starting the Tensorflow Model Server:
			- The Flask Application also starts the Tensorflow Model Server on another port of the machine and then send the prediction request using the received images and models to that Tensorflow Model Server.
		#### 3. Reponse to Auto-deployment Server:
			- Upon receiving the prediction result from Tensorflow Model Server, the application will send back a HTTP response to the Auto-deployment Server.
