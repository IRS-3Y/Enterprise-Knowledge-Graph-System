FROM nginx:1.15.12

# set environment
ENV EKGS_ROOT /ekgs
WORKDIR ${EKGS_ROOT}

# copy application to web root directory
COPY . ${EKGS_ROOT}
RUN rm $EKGS_ROOT/Dockerfile

# replace nginx configuration
RUN rm /etc/nginx/conf.d/*.conf
RUN mv $EKGS_ROOT/*.conf /etc/nginx/conf.d/