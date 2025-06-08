FROM ubuntu:latest
LABEL authors="zlati"

ENTRYPOINT ["top", "-b"]