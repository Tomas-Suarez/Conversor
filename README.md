# 📱 Conversor de Divisas (Dólar - Euro)

### 📝 Descripción de la App
Esta es una aplicación móvil desarrollada para **Android** que permite realizar conversiones de moneda en tiempo real entre **Dólares (USD)** y **Euros (EUR)**.

La aplicación permite al usuario:
* **Seleccionar la moneda de destino** mediante RadioButtons.
* **Personalizar la tasa de cambio:** El usuario puede modificar el valor del Euro manualmente para ajustar la conversión a la tasa del día.
* **Mensajes** Nos permite ver si hay algun campo invalido o el resultado.

---

### 👥 Integrantes del Grupo
* **Suarez, Tomas Agustin** - DNI: `44.642.586`

---

### 🏗️ Implementación de MVVM
Para este proyecto se utilizó la arquitectura **MVVM (Model-View-ViewModel)**, se implemento de la siguiente forma: 

#### 1. Model (`CurrencyModel`)
Representa la estructura de los datos (Nombre de la moneda, valor y símbolo). Es una entidad que transporta la información entre las capas.

#### 2. ViewModel (`MainActivityViewModel`)
* Realiza la **logica de negocio** (multiplicación/división).
* Gestiona el **estado de los datos** usando `MutableLiveData`.
* Expone métodos como `convertCurrency()` y `changeValueCurrency()` para que la vista los utilice.

#### 3. View (`MainActivity`)
Su única responsabilidad es la **interfaz de usuario**:
* Utiliza **View Binding** para acceder de forma segura y mas facil a los componentes del XML.
* **Observa los cambios** en el ViewModel a través de `LiveData`. Cuando el ViewModel actualiza un resultado, la Activity lo mostrara en la pantalla de manera automaticamente.
* **Eventos:** Captura las interacciones del usuario (clics en botones) y llama al ViewModel.

---