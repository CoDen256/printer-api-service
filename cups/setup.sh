#!/usr/bin/bash


# https://support.brother.com/g/b/downloadhowto.aspx?c=us_ot&lang=en&prod=mfcl2710dw_us_eu_as&os=128&dlid=dlf103526_000&flang=4&type3=10283
sudo apt-get update &&  sudo apt-get install -y cups # cups-client cups-bsd cups-filters
sudo cp /etc/cups/cupsd.conf /etc/cups/cupsd.conf.original
sudo chmod a-w /etc/cups/cupsd.conf.original
sudo reboot

sudo netstat -tulpn | grep LISTEN
sudo sed -i "s/Listen localhost/Listen 0.0.0.0/g" /etc/cups/cupsd.conf
sudo cat /etc/cups/cupsd.conf | grep Listen
sudo cupsctl --remote-admin --remote-any --share-printers

journalctl -u cups.service
sudo netstat -tulpn | grep LISTEN

# Upgrade Required Cups Fix
sudo usermod -a -G lpadmin coden
sudo systemctl restart cups


wget https://download.brother.com/welcome/dlf103526/mfcl2710dwpdrv-4.0.0-1.i386.deb
sudo dpkg  -i  --force-all mfcl2710dwpdrv-4.0.0-1.i386.deb
sudo dpkg  -l  |  grep  Brother

# 1) Use default under the name of MFCL2710DW
lpoptions -d MFCL2710DW

# 2) Or add manually as in manual of brother
# http://rpi-beta.local:631/admin/ ->
# -> Add printer -> LPD/LPR Host or Printer -> lpd://192.168.0.194/binary_p1 (printer ip) ->
# -> Share -> Make: Brother -> Continue -> Brother MFCL2710DW for CUPS (en) [the driver installed previously]
# remove the one by default
# lpadmin -x MFCL2710DW
# lpoptions -d Brother

# Maybe just upload ppd file?
# Maybe prebuild the image for rpi with driver already in.