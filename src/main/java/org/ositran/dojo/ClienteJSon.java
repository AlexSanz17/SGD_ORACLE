/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

public class ClienteJSon{
	
	private String identificacion;
        
        private String codTipoInstitucion;
        
        private String nroIdentificacion;

	private String razonSocial;
	
	private String nombre;
	
	private String apellidoPaterno;
        
        private String apellidoMaterno;
	
	private String direccion;
	
	private String distrito;
	
	private String provincia;
	
	private String departamento;
	
	private String telefono;
	
	private String correo;
        
        private String codCargo;
        
        private String codRespuesta;
        
        private String desCargo;

        public String getDesCargo() {
            return desCargo;
        }

        public void setDesCargo(String desCargo) {
            this.desCargo = desCargo;
        }

        public ClienteJSon(){
            
        }
        public ClienteJSon(String nroIdentificacion, String identificacion, String codTipoInstitucion, String razonsocial, String nombre, String apellidoPaterno,
                           String apellidoMaterno, String departamento, String provincia, String distrito, String direccion,
                           String telefono, String correo, String codCargo, String codRespuesta, String desCargo
                           ){
            this.nroIdentificacion = nroIdentificacion==null?"":nroIdentificacion;
            this.identificacion = identificacion==null?"":identificacion;
            this.razonSocial = razonsocial==null?"":razonsocial;
            this.nombre = nombre==null?"":nombre;
            this.apellidoPaterno = apellidoPaterno==null?"":apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno==null?"": apellidoMaterno;
            this.departamento = departamento == null?"": departamento;
            this.provincia = provincia == null?"":provincia;
            this.distrito = distrito == null?"":distrito;
            this.direccion = direccion == null?"":direccion;
            this.telefono = telefono == null?"": telefono;
            this.correo = correo == null?"":correo;
            this.codCargo = codCargo == null?"":codCargo;
            this.codTipoInstitucion = codTipoInstitucion==null?"":codTipoInstitucion; 
            this.codRespuesta = codRespuesta;
            this.desCargo = desCargo;
        }
   
          public String getCodRespuesta() {
        return codRespuesta;
    }

        public void setCodRespuesta(String codRespuesta) {
            this.codRespuesta = codRespuesta;
        }
         public String getApellidoPaterno() {
            return apellidoPaterno;
        }

        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }

        public String getApellidoMaterno() {
            return apellidoMaterno;
        }

        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }
        
        public String getNroIdentificacion() {
            return nroIdentificacion;
        }

        public void setNroIdentificacion(String nroIdentificacion) {
            this.nroIdentificacion = nroIdentificacion;
        }
        
       
        
        public String getCodTipoInstitucion() {
            return codTipoInstitucion;
        }

        public void setCodTipoInstitucion(String codTipoInstitucion) {
            this.codTipoInstitucion = codTipoInstitucion;
        }

        public String getCodCargo() {
            return codCargo;
        }

        public void setCodCargo(String codCargo) {
            this.codCargo = codCargo;
        }
        

	public String getIdentificacion(){
		return identificacion;
	}

	public void setIdentificacion(String identificacion){
		this.identificacion=identificacion;
	}

	public String getRazonSocial(){
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial){
		this.razonSocial=razonSocial;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	
	public String getDireccion(){
		return direccion;
	}

	public void setDireccion(String direccion){
		this.direccion=direccion;
	}

	public String getDistrito(){
		return distrito;
	}

	public void setDistrito(String distrito){
		this.distrito=distrito;
	}

	public String getProvincia(){
		return provincia;
	}

	public void setProvincia(String provincia){
		this.provincia=provincia;
	}

	public String getDepartamento(){
		return departamento;
	}

	public void setDepartamento(String departamento){
		this.departamento=departamento;
	}

	public String getTelefono(){
		return telefono;
	}

	public void setTelefono(String telefono){
		this.telefono=telefono;
	}

	public String getCorreo(){
		return correo;
	}

	public void setCorreo(String correo){
		this.correo=correo;
	}
	
}
