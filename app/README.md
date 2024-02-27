# README

## Gofun ITOS Service APP
- `Gofun ITOS service` es una mini app desarrollada para los dispositivos Android ITOS (CM35) que permite imprimir tickets en segundo plano.
- minSDK 23, targetSDK 33

## Guía de uso
- Las formas de imprimir en segundo plano son:
  - **BroadcastReceiver** (No requiere la app en ejecución): 
``
    val intent = Intent("com.gofun.itos_service.ACTION_PRINT_DATA_RECEIVER")
    intent.setPackage("com.gofun.itos_service")
    intent.putExtra("DATA_TO_PRINT", printableItems)
    sendBroadcast(intent)
``
  - **Service** (Requiere la app en ejecución): 
``
    val intent = Intent("com.gofun.itos_service.ACTION_PRINT_DATA_SERVICE")
    intent.setPackage("com.gofun.itos_service")
    intent.putExtra("DATA_TO_PRINT", printableItems)
    startService(intent)
``
- Los items que se pueden imprimir son **Texto**, **QR** o **Imagen**. En el MainActivity de la app hay un ejemplo de PrintableItems

    
