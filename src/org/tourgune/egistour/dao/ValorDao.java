package org.tourgune.egistour.dao;

import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Muestra;
import org.tourgune.egistour.bean.Opcion;
import org.tourgune.egistour.bean.Valor;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.rowmapper.MuestraRowMapper;
import org.tourgune.egistour.dao.rowmapper.OpcionRowMapper;
import org.tourgune.egistour.dao.rowmapper.ValorRowMapper;
import org.tourgune.egistour.dao.rowmapper.VariableRowMapper;
import org.tourgune.egistour.utils.TablasBD;


@Service
public class ValorDao {

	public List<Valor> getListaValores(int idvariable,
			int idaplicacion, String fecha_desde, String fecha_hasta) {
		String consultaCompleta = "";
		if (fecha_desde.equals(fecha_hasta)) {
			
			consultaCompleta="select count(*) cantidad, valorvariable from valores where idusuario in (select distinct(idusuario) from puntos where fecha::date =" + fecha_desde + "::date) and idvariable="+idvariable+ " group by valorvariable order by valorvariable";
			
		} else {
			
			consultaCompleta="select count(*) cantidad, valorvariable from valores where idusuario in (select distinct(idusuario) from puntos where fecha::date between " + fecha_desde
					+ "::date and " + fecha_hasta + "::date) and idvariable="+idvariable+ " group by valorvariable order by valorvariable";
			
		}
		List<Valor> listaValor = null;
		try {

			listaValor = (List<Valor>) jdbcTemplate.query(consultaCompleta,	new ValorRowMapper());
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}

		return listaValor;
	}
	

	
	public List<Opcion> getOpciones(int idVariable) {

		List<Opcion> listaOp = new ArrayList();

		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "idvariable, opcion";
			String tableList = TablasBD.TABLE_OPTIONS + " ";
			String condition = "WHERE idvariable= ? ";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idVariable };
			int[] types = new int[] { Types.INTEGER };

			try {
				listaOp = (List<Opcion>) jdbcTemplate.query(sql.toString(),
						parametros, types, new OpcionRowMapper());
			} catch (final EmptyResultDataAccessException e) {

			}

		} catch (Exception e) {
			System.err.println(e.getMessage());

		}
		return listaOp;

	}
	
	public  List<Valor> getEstadisticasMulti(int idvariable,String fecha_desde, String fecha_hasta)
	{
		
		
		List<Valor> listaValores = new ArrayList<Valor>();
		List<Opcion> listaOpciones = getOpciones(idvariable);
		
		for (Opcion opcion:listaOpciones)
		{
			String op = opcion.getNombreopcion();
			
			
			
			String selectCantidad = "select count(distinct(idusuario)) from " +
									"valores where idvariable="+idvariable+" and " +
									"regexp_split_to_array (valorvariable,';')::text[] " +
									"@> ARRAY['"+op+"']::text[] ='t' " +
									" and idusuario in (select distinct(idusuario) from puntos where fecha::date between " + fecha_desde
													    + "::date and " + fecha_hasta + "::date)";
			StringBuffer sql = new StringBuffer();
			sql.append(selectCantidad);
			
		
			
			int cantidad = jdbcTemplate.queryForInt(selectCantidad);
			Valor valor = new Valor();
			valor.setCantidad(cantidad);
			valor.setValorvariable(String.valueOf(op));
			listaValores.add(valor);

			
		}
		return listaValores;
	}
	
	
	private List<Valor> getEstadisticasInt(int idvariable,List<Valor> listaValor) {
		
		Variable variable=getVariableMinMax(idvariable);
		int diferencia= (int) (variable.getMax()-variable.getMin());
		//Para establecer los rangos y la cantidad en cada una de ellos
		String[][] rangosValores = new String[10][2];
		//Para almacenar los valores mínimos y máximos
		String[][] minMaxValores = new String[10][2];
		//Se quiere que la diferencia sea un número entero, para ellos se comprueba siempre que el resto sea 0
		if (diferencia%10!=0){
			for(int i=1;i<9;i++){
				diferencia=diferencia+1;
				if (diferencia%10==0){
					diferencia=diferencia/10;
					break;
				}
			}
		}
		else
			diferencia = diferencia/10;

		diferencia=diferencia-1;
		int min=(int) variable.getMin();
		int max=min+diferencia;
		System.out.println("Maximo"+max);
		System.out.println("Minimo"+min);
		System.out.println("Diferencia"+diferencia);
		
		minMaxValores[0][0]=min+"";
		minMaxValores[0][1]=max+"";
		rangosValores[0][0]=min+"-"+max;
		rangosValores[0][1]="0";
		//se calculan los rangos y se añaden las cantidades=0 a todos los rangos
		for(int i=1; i<10;i++){
			min=max+1;
			max=min+diferencia;
			minMaxValores[i][0]=String.valueOf(min);
			minMaxValores[i][1]=String.valueOf(max);
			if (i==9){
				rangosValores[i][0]=min+">";
				rangosValores[i][1]="0";
			}else{
				rangosValores[i][0]=min+"-"+max;
				rangosValores[i][1]="0";
			}
	
		}
		//Se comprueba cada uno de los elementos de la lista a que rango corresponde
		//Posteriormente se suma la cantidad a cada rango
		for (int i = 0; i < listaValor.size(); i++) {
			for(int j = 0; j < minMaxValores.length; j++){
				String cantidad=rangosValores[j][1];
				int valorsumando=Integer.parseInt(cantidad)+listaValor.get(i).getCantidad();
				if ((Integer.parseInt(minMaxValores[j][0])) <= (Integer.parseInt(listaValor.get(i).getValorvariable()))&&((Integer.parseInt(listaValor.get(i).getValorvariable())<=(Integer.parseInt(minMaxValores[j][1]))))) {
					rangosValores[j][1]=String.valueOf(valorsumando);
				}else{
					if(Integer.parseInt(listaValor.get(i).getValorvariable())>=(Integer.parseInt(minMaxValores[9][0]))){
						rangosValores[9][1]=String.valueOf(valorsumando);
					}
				}
			}
		}
		//Se genera una lista de Valor-es donde se alamcenarán los rangos y sus cantidades
		List<Valor> listaValorTotal = new ArrayList<Valor>();;
		for(int i=0; i<10;i++){
			Valor valor=new Valor();
			valor.setValorvariable(rangosValores[i][0]);
			valor.setCantidad(Integer.parseInt(rangosValores[i][1]));
			listaValorTotal.add(valor);
		}
		
		return listaValorTotal;
	}
	
	private List<Valor> getEstadisticasDecimal(int idvariable,List<Valor> listaValor) {
		Variable variable=getVariableMinMax(idvariable);
        double diferencia= (variable.getMax()-variable.getMin())/10;
                   
        DecimalFormat df = new DecimalFormat("##0.00");
        String decimalString = df.format(diferencia).replace(',', '.');
        diferencia = Double.parseDouble(decimalString);
        String[][] rangosValores = new String[10][2];
        String[][] minMaxValores = new String[10][2];
       
        double min=Double.parseDouble(df.format((double) variable.getMin()).replace(',', '.'));
        double max=Double.parseDouble(df.format(min+diferencia).replace(',', '.'));;
        minMaxValores[0][0]=Double.toString(min);
        minMaxValores[0][1]=Double.toString(Double.parseDouble(df.format(min+diferencia).replace(',', '.')));
       
        rangosValores[0][0]=min+"-"+max;
        rangosValores[0][1]="0";
        for(int i=1; i<10;i++){
              min=Double.parseDouble(df.format(max+0.01).replace(',', '.'));
              max=Double.parseDouble(df.format(min+diferencia).replace(',', '.'));
              minMaxValores[i][0]=String.valueOf(min);
              minMaxValores[i][1]=String.valueOf(max);
              if (i==9){
                    rangosValores[i][0]=min+">";
                    rangosValores[i][1]="0";
              }else{
                    rangosValores[i][0]=min+"-"+max;
                    rangosValores[i][1]="0";
              }
             
             
        }
        for (int i = 0; i < listaValor.size(); i++) {
              for(int j = 0; j < minMaxValores.length; j++){
                    String cantidad=rangosValores[j][1];
                    int valorsumando=Integer.parseInt(cantidad)+listaValor.get(i).getCantidad();
                    if ((Double.parseDouble(minMaxValores[j][0])) <= (Double.parseDouble(listaValor.get(i).getValorvariable()))&&((Double.parseDouble(listaValor.get(i).getValorvariable())<=(Double.parseDouble(minMaxValores[j][1]))))) {
                         rangosValores[j][1]=String.valueOf(valorsumando);
                    }else{
                          if(Double.parseDouble(listaValor.get(i).getValorvariable())>=(Double.parseDouble(minMaxValores[9][0]))){
                               rangosValores[9][1]=String.valueOf(valorsumando);
                         }
                    }
              }
        }
        List<Valor> listaValorTotal = new ArrayList<Valor>();;
        for(int i=0; i<10;i++){
              Valor valor=new Valor();
              valor.setValorvariable(rangosValores[i][0]);
              valor.setCantidad(Integer.parseInt(rangosValores[i][1]));
              listaValorTotal.add(valor);
        }
       
        return listaValorTotal;
	}
	public List<Valor> getEstadisticas(int idvariable,
			int idaplicacion, String fecha_desde, String fecha_hasta, int tipo) {
		
		int tipoVariable=tipo;
		List<Valor> listaValor = null;
		List<Valor> listaValortotal=null;
		listaValor=getListaValores(idvariable,idaplicacion, fecha_desde,fecha_hasta);
		if (tipoVariable==1){
			listaValortotal =getEstadisticasInt(idvariable,listaValor);
			return listaValortotal;
		}else{
			if (tipoVariable==4){
				listaValortotal =getEstadisticasDecimal(idvariable,listaValor);
				return listaValortotal;
			}else{
				if (tipoVariable==2){
					listaValortotal=getEstadisticasMulti(idvariable,fecha_desde, fecha_hasta);
					return listaValortotal;
				}else{
					return listaValor;
				}
				
			}
		}
			
	}
	
	public List<Muestra> getMuestra (int idaplicacion, String fecha_desde, String fecha_hasta)
	{
		
//		System.out.println("fecha desde =" + fecha_desde);
//		System.out.println("fecha hasta =" + fecha_hasta);
		String consulta;
		if (fecha_desde.length()<2 && fecha_hasta.length()<2)
			consulta= "select count(*) cantidad, a.fecha fecha from (select fecha::date fecha, idusuario usuario from puntos where idusuario in (select idusuario from usuarios where idaplicacion="+idaplicacion+") group by fecha::date, idusuario) a group by a.fecha";
		else
			consulta = "select count(*) cantidad, a.fecha fecha from (select fecha::date fecha, idusuario usuario from puntos where idusuario in (select idusuario from usuarios where idaplicacion="+idaplicacion+") and fecha::date between " +fecha_desde+"::date and "+ fecha_hasta +"::date group by fecha::date, idusuario) a group by a.fecha";
		
		List<Muestra> listaMuestra = null;
		try {

			listaMuestra = (List<Muestra>) jdbcTemplate.query(consulta,	new MuestraRowMapper());
		} catch (final EmptyResultDataAccessException e) {
			return null;
		}
		return listaMuestra;
	}

	public Variable getVariableMinMax(int idvariable) {

		try {
			StringBuffer sql = new StringBuffer();

			String rowList = "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta,multiopcion,obligatorio ";
			String tableList = TablasBD.TABLE_VARIABLES + "  ";
			String condition = "WHERE idvariable= ?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idvariable };
			int[] types = new int[] { Types.INTEGER };

			Variable variable;

			try {
				variable = jdbcTemplate.queryForObject(sql.toString(),
						parametros, types, new VariableRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return variable;

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	@Resource
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
