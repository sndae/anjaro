#!/bin/sh

stringZ=abcABC123ABCabc
#       |----|          shortest
#       |----------|    longest

echo ${stringZ%%ABC123ABCabc}      # 123ABCabc
# Strip out shortest match between 'a' and 'C'.

daemon_pid="3210 Listening for transport dt_socket at address: 8000"
test=${daemon_pid%%" Listening for transport dt_socket at address: 8000"}
echo $test
