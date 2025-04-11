FROM debian:bullseye

# Instalar paquetes esenciales
# Configurar backports y actualizar paquetes
RUN echo "deb http://deb.debian.org/debian bullseye-backports main" >> /etc/apt/sources.list && \
    apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y -t bullseye-backports curl zip unzip wget git && \
    rm -rf /var/lib/apt/lists/*

# Instalar SDKMAN!
RUN useradd -m pi && echo "pi:raspberry" | chpasswd && adduser pi sudo
RUN su pi -c "curl -s 'https://get.sdkman.io' | bash"
RUN su pi -c "bash -c 'source /home/pi/.sdkman/bin/sdkman-init.sh && sdk install java 21-tem && sdk use java 21-tem && sdk install gradle 8.13'"

# Establecer el usuario no root (opcional)
USER pi
RUN echo "source /home/pi/.sdkman/bin/sdkman-init.sh" >> /home/pi/.bashrc
# Establecer el directorio de trabajo
WORKDIR /home/pi

# Crear directorio para el WAR
RUN mkdir -p /home/pi/app/war

# Definimos el puton de montajke
VOLUME /home/pi/app/war

#ejecutamos el comando
CMD ["bash", "run-prod.sh"]


