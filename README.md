# README

## Gofun LANDI Service APP
- `Gofun LANDI service` es una mini app desarrollada para los dispositivos Android LANDI que permite ejecutar acciones en segundo plano.
- minSDK 24, targetSDK 33

## Guía de uso
- Llamar desde una app externa a través de intent:
``
  val intent = Intent("com.gofun.landi_service.GET_DEVICE_TAG")
  intent.putExtra("DEVICE_TAG", "IMEI")
  startActivityForResult(intent, REQUEST_CODE)
``
- Recoger el resultado en la actividad:
``
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
          val result = data?.getStringExtra("IMEI")
      }
      if (resultCode == RESULT_CANCELED){
          Toast.makeText(this, "GOFUN-LANDI-SERVICE ERROR", Toast.LENGTH_SHORT).show()
      }
  }
``