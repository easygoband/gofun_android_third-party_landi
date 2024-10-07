# README

## Gofun LANDI Service APP
- `Gofun LANDI service` es una mini app desarrollada para los dispositivos Android LANDI que permite ejecutar acciones en segundo plano.
- minSDK 24, targetSDK 33

## Guía de uso
- GetDeviceInfoBroadcastReceiver:
  - **BroadcastReceiver** (No requiere la app en ejecución): 
``
    val intent = Intent("com.gofun.landi_service.ACTION_GET_DEVICE_INFO_RECEIVER")
    intent.setPackage("com.gofun.landi_service")
    intent.putExtra("DATA_TO_PRINT", printableItems)
    sendBroadcast(intent)
``
- GetDeviceInfoService
  - **Service** (Requiere la app en ejecución): 
``
    val intent = Intent("com.gofun.landi_service.ACTION_GET_DEVICE_INFO_SERVICE")
    intent.setPackage("com.gofun.landi_service")
    intent.putExtra("DEVICE_TAG", deviceTag)
    startService(intent)
``

    
