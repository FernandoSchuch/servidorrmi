@echo off
cd servidorrmi\build\classes
echo Gerando Stubs...
echo[
echo[


rmic trabalhormiserver.Servidor
echo[
echo[

timeout 3
cls

echo Publicando... (rmiregistry na porta 1234)...
echo[

start rmiregistry 1234
echo Criado uma nova instancia de terminal para o rmiregistry...
echo[

echo ============= INICIANDO ALGORITMO SERVIDOR =============
cd ../
cd ../
java -jar dist\TrabalhoRMIServer.jar