FROM ubuntu:22.04

# Install necessary packages
RUN apt-get update && \
    apt-get install -y vim git openjdk-17-jdk maven

# Set the working directory to /root/project
WORKDIR /root/project

# Copy run.sh to /root/project in the container
COPY run.sh .

# Set the default command to run bash
CMD ["bash"]



