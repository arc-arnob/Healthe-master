import py_eureka_client.eureka_client as eureka
import django.core.management.commands.runserver as runserver
import  socket
import sys

server_port = int(sys.argv[-1])



registry_client = eureka.init(eureka_server="http://127.0.0.1:8761",
                                                app_name="heartattack-app",
                                                instance_ip='127.0.0.1',
                                                instance_port=server_port)