FROM nginx:1.22-alpine
WORKDIR /usr/src/servisofts

RUN apk update \
  && apk upgrade 

# Instalamos el java
RUN apk add ca-certificates \
  && update-ca-certificates \
  && apk add --update coreutils && rm -rf /var/cache/apk/* \ 
  && apk add --update openjdk11 tzdata curl unzip bash


# Removemos la cache
RUN apk add --no-cache nss \
  && rm -rf /var/cache/apk/*

# CONFIRGURAMOS EL REPO
ARG PORT_UPLOAD
ENV PORT_UPLOAD $PORT_UPLOAD
EXPOSE ${PORT_UPLOAD}

COPY . .
# RUN cd java \ 
#     bash compile.sh 

WORKDIR /usr/src/servisofts/java

CMD ["bash", "compile.sh"]
