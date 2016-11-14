#curl -X POST -d '{"jsonrpc": "2.0","method":"deploy", "params": {"type": 1,"chaincodeID":{"name":"SimpleSample"},"ctorMsg": {"args":["init", "a", "10", "b", "20"]}},"id": 3}' http://127.0.0.1:7051 
curl -X POST http://127.0.0.1:7050/registrar -d '{"enrollId":"jim","enrollSecret":"6avZQLwcUe9b"}'

curl -X POST -d '{"jsonrpc": "2.0","method":"deploy", "params": {"type": 1,"chaincodeID":{"name":"couponcc"},"ctorMsg": {"args":["init"]}, "secureContext":"jim"},"id": 3}' http://127.0.0.1:7050/chaincode 
curl -X POST -d '{"jsonrpc": "2.0","method":"invoke", "params": {"type": 1,"chaincodeID":{"name":"couponcc"},"ctorMsg": {"args":["createCouponBatch"]}, "secureContext":"jim"},"id": 5}' http://127.0.0.1:7050/chaincode 
exit 0
curl -X POST -d '{"jsonrpc": "2.0","method":"invoke", "params": {"type": 1,"chaincodeID":{"name":"SimpleSample"},"ctorMsg": {"args":["invoke", "a", "b", "10"]}, "secureContext":"jim"},"id": 6}' http://127.0.0.1:7050/chaincode 

curl -X POST -d '{"jsonrpc": "2.0","method":"query", "params": {"type": 1,"chaincodeID":{"name":"SimpleSample"},"ctorMsg": {"args":["query", "a"]}, "secureContext":"jim"},"id": 7}' http://127.0.0.1:7050/chaincode 

curl -X POST -d '{"jsonrpc": "2.0","method":"query", "params": {"type": 1,"chaincodeID":{"name":"SimpleSample"},"ctorMsg": {"args":["query", "b"]}, "secureContext":"jim"},"id": 8}' http://127.0.0.1:7050/chaincode 
