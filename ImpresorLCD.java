import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImpresorLCD {
    
    // Puntos fijos
    //private final int[] pf1;
    int[] pf1 =new int[2];
    int[] pf2 =new int[2];
    int[] pf3 =new int[2];
    int[] pf4 =new int[2];
    int[] pf5 =new int[2];
    String[][] matrizImpr;

    String CARACTER_VERTICAL = "|";
    String CARACTER_HORIZONTAL = "-";
    String POSICION_X = "X";
    String POSICION_Y = "Y";
    int pivotX = 0;
    String v_Sarta = "";

    // TODO code application logic here
    //String entrada = JOptionPane.showInputDialog("Digite el numero");
    int size;

    // Calcula el numero de filasDig
    int filasDig;
    int columDig;
    int totalFilas;
    int totalColum;

   
    public void adicionarLinea(int size, String posFija, int[] punto, String caracter) {

        if (posFija.equalsIgnoreCase(POSICION_X)) 
        {
            for (int y = 1; y <= size; y++) 
            {
                int valor = punto[1] + y;
                matrizImpr[punto[0]][valor] = caracter;
            }
        } 
        else 
        {
            for (int i = 1; i <= size; i++) 
            {
                int valor = punto[0] + i;
                matrizImpr[valor][punto[1]] = caracter;
            }
        }
    }
    /**
     *Juanito
     */
    public void adicionarSegmento(String segmento) {
        String[] Valores;
		Valores = segmento.split(",");
		
		for(int i = 0; i<Valores.length -1; i++){
			switch (Valores[i]) {
				case "1":
					adicionarLinea(this.size, POSICION_Y, this.pf1, CARACTER_VERTICAL);
					break;                   
				case "2":                      
					adicionarLinea(this.size, POSICION_Y, this.pf2, CARACTER_VERTICAL);
					break;                   
				case "3":                      
					adicionarLinea(this.size, POSICION_Y, this.pf5, CARACTER_VERTICAL);
					break;                   
				case "4":                      
					adicionarLinea(this.size, POSICION_Y, this.pf4, CARACTER_VERTICAL);
					break;                   
				case "5":                      
					adicionarLinea(this.size, POSICION_X, this.pf1, CARACTER_HORIZONTAL);
					break;                   
				case "6":                      
					adicionarLinea(this.size, POSICION_X, this.pf2, CARACTER_HORIZONTAL);
					break;                   
				case "7":                      
					adicionarLinea(this.size, POSICION_X, this.pf3, CARACTER_HORIZONTAL);
					break;
				default:
					break;
			}
		}
    }
	
    /**
     *Juanito
     */
    public static String adicionarDigito(int In_numero) {
        
		String v_Rta = ""; 

        switch (In_numero) {
            case 1:
                v_Rta = "3,4";
                break;
            case 2:
                v_Rta = "5,3,6,2,7";
                break;
            case 3:
                v_Rta = "5,3,6,4,7";
                break;
            case 4:
                v_Rta = "1,6,3,4";
                break;
            case 5:
                v_Rta = "5,1,6,4,7";
                break;
            case 6: //EN EL NUMERO SEIS LA SECUENCIA DE LOS SEGMENTOS SE ENCONTRABA MAL MAPEADA
                v_Rta = "5,1,6,2,4,7";
                break;
            case 7:
                v_Rta = "5,3,4";
                break;
            case 8:
                v_Rta = "1,2,3,4,5,6,7";
                break;
            case 9:
                v_Rta = "1,3,4,5,6,7";
                break;
            case 0:
                v_Rta = "1,2,3,4,5,7";
                break;
            default:
                break;
        }
		return v_Rta;       
    }
		
	/**
     *Juanito
     */
    public void imprimirNumero(String numeroImp, int espacio) 
    {
        //^_^ int pivotX = 0;
        char[] digitos;

		pivotX = 0;	
        // Calcula el numero de filas cada digito
        this.totalFilas = (2 * this.size) + 3;

        // Calcula el numero de columna de cada digito
        this.columDig = this.size + 2;

        // Calcula el total de columnas de la matriz en la que se almacenaran los digitos
        this.totalColum = (this.columDig * numeroImp.length()) + (espacio * numeroImp.length());

        // crea matriz para almacenar los numero a imprimir
        this.matrizImpr = new String[this.totalFilas][this.totalColum];

        // Inicializa matriz
        for (int i = 0; i < this.totalFilas; i++) {
            for (int j = 0; j < this.totalColum; j++) {
                this.matrizImpr[i][j] = " ";
            }
        }
		
        for (int i = 0; i < numeroImp.length(); i++) {
            
            int numero = Integer.parseInt(numeroImp.substring(i,1));
            //Valida que el caracter sea un digito
            if(!Character.isDigit(numero))
            {
                throw new IllegalArgumentException("Caracter " + numero + " no es un digito");
            }


            //Calcula puntos fijos
            this.pf1[0] = 0;
            this.pf1[1] = 0 + pivotX;
            
            this.pf2[0] = (this.totalFilas / 2); //AQUI SE DEBE TENER EN CUENTA QUE SE DEBE TOMAR EL NUMERO ENTERO Y NO APROXIMARLO; SI LO ESTA APROXIMANDO
            this.pf2[1] = 0 + pivotX;

            this.pf3[0] = (this.totalFilas - 1);
            this.pf3[1] = 0 + pivotX;

            this.pf4[0] = (this.columDig - 1);
            this.pf4[1] = ((this.totalFilas / 2) + pivotX); //AQUI SE DEBE TENER EN CUENTA QUE SE DEBE TOMAR EL NUMERO ENTERO Y NO APROXIMARLO; SI LO ESTA APROXIMANDO

            this.pf5[0] = 0;
            this.pf5[1] = (this.columDig - 1) + pivotX;

            pivotX = pivotX + this.columDig + espacio;

            adicionarSegmento(adicionarDigito(numero));
        }

        // Imprime matriz
        for (int i = 0; i < this.totalFilas; i++) {
            for (int j = 0; j < this.totalColum; j++) {
				v_Sarta = this.matrizImpr[i][j];
            }
            System.out.println(v_Sarta);
			v_Sarta = "";
        }
    }

	/**
     *Juanito
     */
    public void procesar(String comando, int espacioDig) {
        
        String[] parametros;
        
        if (!comando.contains(",")) {
            throw new IllegalArgumentException("Cadena " + comando + " no contiene caracter ,");
        }
        
        //Se hace el split de la cadena
        parametros = comando.split(",");
        
        //Valida la cantidad de parametros permitidos
        if(parametros.length>2 || parametros.length<2)
        {
           throw new IllegalArgumentException("Cadena " + comando + " contiene mas o menos paramentros de entrada requeridos."); 
        }
		
		//Valido si son numero los parametros de entrada
		for(int i = 0; i<parametros.length; i++){
			if(!isNumeric(parametros[i])){
				throw new IllegalArgumentException("Parametro Size [" + parametros[i] + "] no es un numero");
				//return;
			}			
		}
        
		//Validacion de terminacion del metodo
		if(fu_FinalizaAplicacion(Integer.parseInt(parametros[0]), Integer.parseInt(parametros[1]))){
				throw new IllegalArgumentException("Finaliza aplicacion");
				//return;			
		}
		
		//ELIMINO LA VARIABLE TAM PORQUE SE TIENE LA VARIABLE SIZE GLOBAL
		size = Integer.parseInt(parametros[0]);
        //Valida que el parametro size sea un numerico            
        // se valida que el size este entre 1 y 10
        if(size <1 || size >10)
        {
            throw new IllegalArgumentException("El parametro size ["+ size + "] debe estar entre 1 y 10");
			//return;
        }
		
        // Realiza la impresion del numero
        imprimirNumero(parametros[1],espacioDig);
    }
	
	/**
     *Juanito
     */
    public void pruebaAutomatica() {
        
        String[] parametros;
        String comando;
        int espacioDig = 1;
        
        for(int y = 1; y < 11; y ++){
            //numero = (int) (Math.random() * n) + 1;
        comando = (int) (Math.random() * y) + 1 + "," +(int) (Math.random() * y) + 1;
        
        if (!comando.contains(",")) {
            throw new IllegalArgumentException("Cadena " + comando + " no contiene caracter ,");
        }
        
        //Se hace el split de la cadena
        parametros = comando.split(",");
        
        //Valida la cantidad de parametros permitidos
        if(parametros.length>2 || parametros.length<2)
        {
           throw new IllegalArgumentException("Cadena " + comando + " contiene mas o menos paramentros de entrada requeridos."); 
        }
		
		//Valido si son numero los parametros de entrada
		for(int i = 0; i<parametros.length; i++){
			if(!isNumeric(parametros[i])){
				throw new IllegalArgumentException("Parametro Size [" + parametros[i] + "] no es un numero");
				//return;
			}			
		}
        
		//Validacion de terminacion del metodo
		if(fu_FinalizaAplicacion(Integer.parseInt(parametros[0]), Integer.parseInt(parametros[1]))){
				throw new IllegalArgumentException("Finaliza aplicacion");
				//return;			
		}
		
		//ELIMINO LA VARIABLE TAM PORQUE SE TIENE LA VARIABLE SIZE GLOBAL
		size = Integer.parseInt(parametros[0]);
        //Valida que el parametro size sea un numerico            
        // se valida que el size este entre 1 y 10
        if(size <1 || size >10)
        {
            throw new IllegalArgumentException("El parametro size ["+ size + "] debe estar entre 1 y 10");
			//return;
        }
		
        // Realiza la impresion del numero
        imprimirNumero(parametros[1],espacioDig);
        }
        
        
    }
	
    /**
     *Juanito
     */
	public static boolean fu_FinalizaAplicacion(int In_Size, int In_Numero){
		if(In_Size == 0 || In_Numero == 0){
			return true;
		}else{
			return false;
		}
	}
    /**
     *
     * Metodo encargado de validar si una cadena es numerica
     *
     * @param cadena Cadena
     */  
    static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

}
