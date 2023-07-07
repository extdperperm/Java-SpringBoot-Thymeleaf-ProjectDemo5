package es.dsw.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import es.dsw.models.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* [1] - GESTIÓN DE COOKIES */
@Controller
public class MainController {  
	 

	@GetMapping(value = {"/","/index"})
	public String index() {
		return "index";
	} 

	
	//En este ejemplo, se crea una cookie denominada Cookie1 cuyo valor es la cadena 'valor1#valor2#valor3'. Notar que hay caracteres que suelen dar problemas, como es el caso del espacio. Un
	//uso común es guardar varios valores en una misma cookie y para ello se puede introducir separadores; en este ejemplo se ha usado el caracter # como separador. Por defecto,
	//el valor de la cookie no está cifrada y un usuario experto puede examinarla desde el navegador.
	
	//RECUERDA: En las cookies solo se debe almacenar la minima información necesaria que es útil más allá de la sesión del usuario. La escritura y lectura de los valores de una cookie
	//es muy costosa en rendimiento si se compara con las variables de sesión. El valor de una cookie no esta serializada, es decir, es una cadena de texto (no intentes guardar un objeto directamente).
	@GetMapping(value = {"/ejemplo1"}) 
	public String ejem4(HttpServletRequest request, HttpServletResponse response) {
		

		//Se crea la cookie indicando su nombre (Cookie1) y el valor (valor1#valor2#valor3).
        Cookie newCookie = new Cookie("Cookie1", "valor1#valor2#valor3");
        
        //Vigencia de la cookie: 24 horas. Como no se indica de forma explícita el dominio, es accesible a todo el dominio de la aplicación.
        newCookie.setMaxAge(24 * 60 * 60); 
        
        //Se añade a la respuesta para que se guarde en el cliente.
        response.addCookie(newCookie);
        
		return "example1"; 
	}
	
	//En este ejemplo se procede a leer el valor de la cookie creada en el ejemplo 4 y mostrar un mensaje en la vista.
	@GetMapping(value = {"/ejemplo2"}) 
	public String ejem5(HttpServletRequest request, Model model) {
		
		//Se obtienen todas las cookies accesibles por el dominio.
		Cookie[] cookies = request.getCookies();
		
		//Se crea un objeto a modo de modelo de negocio para alimentar la vista
		CookieData objCookieData = new CookieData();
		
		//Se inicializa el resultado o información que se mostrará en la vista. Por defecto, se asume que la cookie no existe.
		objCookieData.setEvaluarCookie("Cookie1 no encontrada o localizada. Para generar esta cookie haga click en el Ejemplo 4.");
		
		//Si existe alguna cookie accesible al dominio de la aplicación.
		if (cookies != null) {
			//Se recorren todas las cookies accesibles hasta localizar la que nos interesa.
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Cookie1")) {
					objCookieData.setEvaluarCookie("El valor de la Cookie1 es: " + cookies[i].getValue());
					break;
				}
			}
		}
		
		//Se carga el modelo del negocio (CookieData) como atributo en el modelo de spring.
		model.addAttribute("CookieData", objCookieData);
		
		return "example2";
	}
	
	//En este ejemplo, se muestra como podemos modificar cualquier atributo de una cookie ya creada. Si no existe, se crea la cookie.
	@GetMapping(value = {"/ejemplo3"}) 
	public String ejem6(HttpServletRequest request, HttpServletResponse response) {
		
		//Se obtienen todas las cookies accesibles por el dominio.
		Cookie[] cookies = request.getCookies();
		
		//Se crea una variable de tipo Cookie
		Cookie cookie = null;
		

		//Si existe alguna cookie accesible al dominio de la aplicación.
		if (cookies != null) {
			//Se recorren todas las cookies accesibles hasta localizar la que nos interesa.
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Cookie1")) {
					cookie = cookies[i];
					break;
				}
			}
		} 
		
		//Si la cookie existe se modifica. Si no existe se crea. Se establece una vigencia de 1 hora.
		if (cookie != null) {
			cookie.setValue("valor4#valor5#valor6");
			cookie.setMaxAge(60*60);
		} else {
			//Se crea la cookie indicando su nombre (Cookie1) y el valor (valor1#valor2#valor3).
			cookie= new Cookie("Cookie1", "valor4#valor5#valor6");
	        
	        //Vigencia de la cookie: 24 horas. Como no se indica dominio, solo es accesible por el dominio de la aplicacion.
			cookie.setMaxAge(60*60); 
		}

        //Para que los cambios surjan efecto, es necesario añadirla a la respuesta del cliente.
        response.addCookie(cookie);
		
		return "example3";
	}
	
	//En este ejemplo, se elimina la cookie. No exíste un método para eliminar la cookie pero para conseguirlo, solo es necesario
	//asignar o al tiempo máximo de vigencia de la cookie.
	@GetMapping(value = {"/ejemplo4"}) 
	public String ejem7(HttpServletRequest request, HttpServletResponse response) {
		
		//Se obtienen todas las cookies accesibles por el dominio.
		Cookie[] cookies = request.getCookies();
		
		//Se crea una variable de tipo Cookie
		Cookie cookie = null;
		

		//Si existe alguna cookie accesible al dominio de la aplicación.
		if (cookies != null) {
			//Se recorren todas las cookies accesibles hasta localizar la que nos interesa.
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("Cookie1")) {
					cookie = cookies[i];
					break;
				}
			}
		} 
		
		//No existe un método como tal para eliminar una cookie, pero si se asigna 0 a MaxAge, el enviarla al cliente se elimina.
		if (cookie != null) {
			//Se fuerza su eliminación.
			cookie.setMaxAge(0); 
	        //Para que los cambios surjan efecto, es necesario añadirla a la respuesta del cliente.
	        response.addCookie(cookie);
		} 

		return "example4";
	}
	
	//En este ejemplo se hace lo mismo que en el Ejemplo 2 pero con ménos código. Se hace uso de la java annotation de spring que permite leer valores de cookies por el nombre. En este caso se declara
	//la cookie como no requerida y si no existe no se produce excepción, sino que en valor de la variable es nula.
	@GetMapping(value = {"/ejemplo5"}) 
	public String ejem8(@CookieValue(name="Cookie1", required=false) String auxCookie, Model model) { //También podría indicarse un defaultValue (Ejemplo: defaultValue = "0").
		
		//Se crea un objeto a modo de modelo de negocio para alimentar la vista
		CookieData objCookieData = new CookieData();
		
		if (auxCookie == null) {
			objCookieData.setEvaluarCookie("Cookie1 no encontrada o localizada. Para generar esta cookie haga click en el Ejemplo 4.");
		} else {
			objCookieData.setEvaluarCookie(auxCookie);
		}
		
		//Se carga el modelo del negocio (CookieData) como atributo en el modelo de spring.
		model.addAttribute("CookieData", objCookieData);
		
		return "example5";
	}

	

}


