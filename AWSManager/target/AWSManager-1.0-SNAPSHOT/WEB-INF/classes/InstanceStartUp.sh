#!/bin/bash

#perl -pi -e 's/^#?Port 22$/Port 80/' /etc/ssh/sshd_config
#service sshd restart || service ssh restart

# set up tensorflow
sudo apt-get update
sudo apt-get install -y python3-pip
sudo apt-get install -y python3-dev

# Configure locale setting
export LC_ALL=C

# Install awscli and tensorflow
pip3 install awscli
pip3 install tensorflow

# Set up ssm for remote connection
wget https://s3.amazonaws.com/ec2-downloads-windows/SSMAgent/latest/debian_amd64/amazon-ssm-agent.deb
sudo dpkg -i amazon-ssm-agent.deb

# mount s3 to instance
sudo apt-get install -y s3fs
sudo echo [YOUR AWS Credential] > /home/ubuntu/.passwd-s3fs
sudo chmod 600 /home/ubuntu/.passwd-s3fs
sudo cp /home/ubuntu/.passwd-s3fs /etc/passwd-s3fs
sudo mkdir /home/ubuntu/s3-drive
sudo sh -c "echo 'user_allow_other' >> /etc/fuse.conf"
sudo s3fs -o allow_other michaelfyp /home/ubuntu/s3-drive

# run training
git clone https://github.com/michaelhoanglong/distributed-tensorflow.git /home/ubuntu/distributed-tensorflow