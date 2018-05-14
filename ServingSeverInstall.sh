export LC_ALL=C

sudo apt-get remove -y tensorflow-model-server

echo "deb [arch=amd64] http://storage.googleapis.com/tensorflow-serving-apt stable tensorflow-model-server tensorflow-model-server-universal" | sudo tee /etc/apt/sources.list.d/tensorflow-serving.list

curl https://storage.googleapis.com/tensorflow-serving-apt/tensorflow-serving.release.pub.gpg | sudo apt-key add -

sudo apt-get update && sudo apt-get install tensorflow-model-server

sudo apt-get update && sudo apt-get install -y \
        build-essential \
        curl \
        libcurl3-dev \
        git \
        libfreetype6-dev \
        libpng12-dev \
        libzmq3-dev \
        pkg-config \
        python-dev \
        python-numpy \
        python-pip \
        software-properties-common \
        swig \
        zip \
        zlib1g-dev

sudo apt-get install libstdc++6
sudo add-apt-repository ppa:ubuntu-toolchain-r/test 
sudo apt-get update
sudo apt-get -y upgrade
sudo apt-get -y dist-upgrade

sudo apt-get install python-opencv


git clone https://github.com/michaelhoanglong/distributed-tensorflow.git

# install flask and grpc, tensorflow-serving-api
sudo apt-get update
sudo apt-get install -y python-pip python-dev nginx
sudo pip install virtualenv
mkdir ~/myproject
cd ~/myproject
# virtualenv myprojectenv
# source myprojectenv/bin/activate
pip install gunicorn 
pip install flask
pip install grpcio
pip install --no-cache-dir tensorflow-serving-api
pip install opencv-python

sudo cp ~/distributed-tensorflow/serving/myproject.py ~/myproject 
sudo cp ~/distributed-tensorflow/serving/wsgi.py ~/myproject 

# deactivate
# sudo echo "[Unit]
# Description=Gunicorn instance to serve myproject
# After=network.target

# [Service]
# User=ubuntu
# Group=www-data
# WorkingDirectory=/home/ubuntu/myproject
# Environment="PATH=/home/ubuntu/myproject/myprojectenv/bin"
# ExecStart=/home/ubuntu/myproject/myprojectenv/bin/gunicorn --workers 3 --bind unix:myproject.sock -m 007 wsgi:app
# logger=/home/ubuntu/myproject/log/site_app_log.log

# [Install]
# WantedBy=multi-user.target" > /etc/systemd/system/myproject.service

# sudo systemctl stop myproject
# sudo systemctl start myproject
# sudo systemctl enable myproject

# sudo echo "server {
#     listen 80;
#     server_name 127.0.0.1;

#     location / {
#         include proxy_params;
#         proxy_pass http://unix:/home/ubuntu/myproject/myproject.sock;
#     }
# }" > /etc/nginx/sites-available/myproject
# sudo ln -s /etc/nginx/sites-available/myproject /etc/nginx/sites-enabled
# sudo nginx -t
# sudo systemctl restart nginx
# sudo ufw allow 'Nginx Full'
gunicorn --bind 0.0.0.0:1500 wsgi:app
# tensorflow_model_server --port=9000 --model_name=mnist --model_base_path=/home/ubuntu/model/
