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
RUN su pi -c "mkdir -p /home/pi/app/war"
RUN su pi -c "mkdir -p /home/pi/projects"
RUN su pi -c "cd /home/pi/projects && git clone https://github.com/AmaiaBerrocal/thermostate.git"

# Establecer el usuario no root (opcional)term
USER pi
RUN echo "source /home/pi/.sdkman/bin/sdkman-init.sh" >> /home/pi/.bashrc
# Establecer el directorio de trabajo
WORKDIR /home/pi

# Podriamos servir solo el war, para eso tendriamos que hacer un volume en donde tenemos el war
# Pero lo que hacemos aqui va a ser bajarnos el codigo y hacer un sun-dev

# Definimos el puton de montajke
# VOLUME /home/pi/app/war

#ejecutamos el comando
CMD ["bash", "-c", "source /home/pi/.sdkman/bin/sdkman-init.sh && ./projects/thermostate/scripts/run-dev.sh"]


