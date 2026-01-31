#!/bin/bash
# install.sh - Install JFIM system-wide on Linux

echo "Installing JFIM..."

# Ensure /opt/jfim exists
sudo mkdir -p /opt/jfim

# Copy all project files to /opt/jfim
sudo cp -r . /opt/jfim

# Create global executable command
sudo bash -c 'echo -e "#!/bin/bash\njava -jar /opt/jfim/jfim.jar \"\$@\"" > /usr/local/bin/jfim'

# Make the command executable
sudo chmod +x /usr/local/bin/jfim
