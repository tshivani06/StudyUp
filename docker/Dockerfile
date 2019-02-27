FROM ubuntu

# File Author / Maintainer
MAINTAINER Maintaner Name

RUN apt-get update
RUN apt-get install -y nano wget dialog net-tools curl

RUN apt-get install -y nginx

RUN rm -v /etc/nginx/nginx.conf

# Copy a configuration file from the current directory
COPY nginx.conf /etc/nginx/

# Append "daemon off;" to the beginning of the configuration
RUN echo "daemon off;" >> /etc/nginx/nginx.conf

COPY docker-entrypoint.sh /root/
RUN chmod +x /root/docker-entrypoint.sh

ENTRYPOINT [ "/root/docker-entrypoint.sh" ]

# Expose ports
EXPOSE 8888
CMD service nginx start
