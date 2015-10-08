package org.tourgune.egistour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.tourgune.egistour.bean.Opcion;
import org.tourgune.egistour.bean.Variable;
import org.tourgune.egistour.dao.rowmapper.OpcionRowMapper;
import org.tourgune.egistour.dao.rowmapper.VariableRowMapper;
import org.tourgune.egistour.utils.AppTrackUtils;
import org.tourgune.egistour.utils.TablasBD;

/**
 * AppTrack
 *
 * Created by CICtourGUNE on 10/04/13.
 * Copyright (c) 2013 CICtourGUNE. All rights reserved.
 * 
 * Clase que implementa la lógica de base de datos sobre las variables asignadas a cada aplicación
 */
@Service
public class VariableDao {

	
	/**
	 * Método que se utiliza para la creación de una variable de tipo Integer
	 * 
	 * @param variable Objeto de tipo variable que contiene la información a insertar
	 * @return devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createVariableInt(Variable variable) {

		try {
			StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_VARIABLES_APPLICATION_ID
					+ ", " + TablasBD.TABLE_VARIABLES_TYPE_ID + ", "
					+ TablasBD.TABLE_VARIABLES_NAME + ", "
					+ TablasBD.TABLE_VARIABLES_MAX + ", "
					+ TablasBD.TABLE_VARIABLES_MIN + ", "
					+ TablasBD.TABLE_VARIABLES_OBLIGATORIO + " ) ";
			String tableList = TablasBD.TABLE_VARIABLES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?,?)");


			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdaplicacion(),
					variable.getIdtipo(), variable.getNombrevariable(),
					variable.getMax(), variable.getMin(),
					variable.getObligatorio() };

			types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR,
					Types.INTEGER, Types.INTEGER, Types.BOOLEAN };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	/**
	 * Método que se utiliza para la creación de una variable de tipo Fecha
	 * 
	 * @param variable Objeto de tipo variable que contiene la información a insertar
	 * @return devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createVariableFecha(Variable variable) {

		try {
			StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_VARIABLES_APPLICATION_ID
					+ ", " + TablasBD.TABLE_VARIABLES_TYPE_ID + ", "
					+ TablasBD.TABLE_VARIABLES_NAME + ", "
					+ TablasBD.TABLE_VARIABLES_DATEINI + ", "
					+ TablasBD.TABLE_VARIABLES_DATEFIN + ", "
					+ TablasBD.TABLE_VARIABLES_OBLIGATORIO + " ) ";
			String tableList = TablasBD.TABLE_VARIABLES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?,?)");

			Object[] parametros;
			int[] types;

			parametros = new Object[] {
					variable.getIdaplicacion(),
					variable.getIdtipo(),
					variable.getNombrevariable(),
					appTrackUtils.convertStringToDate(variable.getFechadesde()),
					appTrackUtils.convertStringToDate(variable.getFechahasta()),
					variable.getObligatorio() };

			types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR,
					Types.DATE, Types.DATE, Types.BOOLEAN };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Método que se utiliza para insertar una opcion en una variable de tipo Opcion
	 * 
	 * @param idVariable Identificador de la variable a la cual se le quiere añadir una opción
	 * @param opcion Nombde de la opción
	 * @return Devuelve un 1 en caso de éxito y -1 en caso contrario
	 */
	public String insertOpcion(int idVariable, String opcion) {

		try {

			StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_OPTIONS_VARIABLE_ID + ", "
					+ TablasBD.TABLE_OPTIONS_OPTION + ") ";

			String tableList = TablasBD.TABLE_OPTIONS;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?)");

			Object[] parametros;
			int[] types;

			parametros = new Object[] { idVariable, opcion };

			types = new int[] { Types.INTEGER, Types.VARCHAR };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Método que sirve para crear una variable de tipo Opcion
	 * 
	 * @param variable Objeto que contiene la información de la variable
	 * @param opciones Lista de opciones separados por ";"
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createVariableOpciones(Variable variable, String opciones) {

		try {
			final StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_VARIABLES_APPLICATION_ID
					+ ", " + TablasBD.TABLE_VARIABLES_TYPE_ID + ", "
					+ TablasBD.TABLE_VARIABLES_NAME + ", "
					+ TablasBD.TABLE_VARIABLES_MULOPT + ", "
					+ TablasBD.TABLE_VARIABLES_OBLIGATORIO + " ) ";
			String tableList = TablasBD.TABLE_VARIABLES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?)");

			final int idAplicacion = variable.getIdaplicacion();
			final String nombreVariable = variable.getNombrevariable();
			final Boolean multiopcion = variable.getMultiopcion();
			final Boolean obligatorio = variable.getObligatorio();

			KeyHolder keyHolder = new GeneratedKeyHolder();
			int idVariable = -1;
			jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(
							sql.toString(),
							java.sql.Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, idAplicacion);
					ps.setInt(2, 2);
					ps.setString(3, nombreVariable);
					ps.setBoolean(4, multiopcion);
					ps.setBoolean(5, obligatorio);

					return ps;
				}
			}, keyHolder);

			Map<String, Object> claves = keyHolder.getKeys();
			idVariable = (Integer) claves.get("idvariable");

			List<String> listaOpciones = appTrackUtils.stringToArray(opciones);
			for (int i = 0; i < listaOpciones.size(); i++) {
				insertOpcion(idVariable, listaOpciones.get(i));
			}

			return "1";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	/**
	 * Método que se utiliza para crear una variable de tipo Decimal
	 * 
	 * @param variable Objeto que contiene toda la información de la variable que se quiere insertar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String createVariableDecimal(Variable variable) {

		try {
			StringBuffer sql = new StringBuffer();
			String rowList = " (" + TablasBD.TABLE_VARIABLES_APPLICATION_ID
					+ ", " + TablasBD.TABLE_VARIABLES_TYPE_ID + ", "
					+ TablasBD.TABLE_VARIABLES_NAME + ", "
					+ TablasBD.TABLE_VARIABLES_MAX + ", "
					+ TablasBD.TABLE_VARIABLES_MIN + ", "
					+ TablasBD.TABLE_VARIABLES_OBLIGATORIO + " ) ";
			String tableList = TablasBD.TABLE_VARIABLES;

			sql.append("INSERT INTO " + tableList + rowList);
			sql.append("VALUES (?,?,?,?,?,?)");


			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdaplicacion(),
					variable.getIdtipo(), variable.getNombrevariable(),
					variable.getMax(), variable.getMin(),
					variable.getObligatorio() };

			types = new int[] { Types.INTEGER, Types.INTEGER, Types.VARCHAR,
					Types.DECIMAL, Types.DECIMAL, Types.BOOLEAN };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Método que devuelve la lista de variables de una determinada aplicación 
	 * 
	 * @param idAplicacion Identificador de la aplicación 
	 * @return Devuelve la lista de variables de una aplicación
	 */
	public List<Variable> getVariables(int idAplicacion) {
		List<Variable> listaVariables = new ArrayList();
		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta,multiopcion,obligatorio";
			String tableList = TablasBD.TABLE_VARIABLES + " ";
			String condition = "WHERE idaplicacion= ? ORDER BY nombrevariable desc";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idAplicacion };
			int[] types = new int[] { Types.INTEGER };

			List<Variable> listaVariablesAux = new ArrayList();
			try {
				listaVariablesAux = (List<Variable>) jdbcTemplate.query(
						sql.toString(), parametros, types,
						new VariableRowMapper());
				Iterator iter = listaVariablesAux.iterator();
				while (iter.hasNext()) {
					Variable var = (Variable) iter.next();
					if (var.getIdtipo() == 2) {

						var.setOpciones(getOpciones(var.getIdvariable()));
					}

					listaVariables.add(var);

				}

			} catch (final EmptyResultDataAccessException e) {

			}

		} catch (Exception e) {
			System.err.println("error" + e.getMessage());

		}

		return listaVariables;

	}

	/**
	 * Método que dado un identificador de variable devuelve la lista de opciones en caso de que fuese de tipo Opción
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar
	 * @return Devuelve la lista de opciones 
	 */
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

	/**
	 * Método que sirve para suprimir una determinada variable
	 * 
	 * @param idVariable Identificador de la variable que se queire eliminar
	 * @return Devuelve 1 en caso de éxito y -1 en caso contrario
	 */
	public String deleteVariable(int idVariable) {
		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			// String rowList =
			// "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta";
			String tableList = TablasBD.TABLE_VARIABLES + " ";
			String condition = "WHERE idvariable= ? ";

			sql.append("DELETE FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idVariable };
			int[] types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	/**
	 * Método que elimina todas las opciones de una determinada variable de tipo Opcion
	 * 
	 * @param idVariable Identificador de la variable 
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String deleteOpciones(int idVariable) {
		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			// String rowList =
			// "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta";
			String tableList = TablasBD.TABLE_OPTIONS + " ";
			String condition = "WHERE idvariable= ? ";

			sql.append("DELETE FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idVariable };
			int[] types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	
	/**
	 * Método que recupera toda la información de una determina variable
	 * 
	 * @param idVariable Identificador de la variable que se quiere consultar
	 * @return Devuelve un objeto de tipo variable que contiene toda la información
	 */
	public Variable getVariable(int idVariable) {
		try {
			StringBuffer sql = new StringBuffer();
			StringBuffer countSql = new StringBuffer();
			String rowList = "idvariable, idaplicacion, idtipo, nombrevariable, max,min, fechadesde,fechahasta,multiopcion,obligatorio ";
			String tableList = TablasBD.TABLE_VARIABLES + "  ";
			String condition = "WHERE idvariable= ?";

			sql.append("SELECT " + rowList + " FROM ");
			sql.append(tableList);
			sql.append(condition);

			Object[] parametros;

			parametros = new Object[] { idVariable };
			int[] types = new int[] { Types.INTEGER };

			Variable variable;

			try {
				variable = jdbcTemplate.queryForObject(sql.toString(),
						parametros, types, new VariableRowMapper());
			} catch (final EmptyResultDataAccessException e) {
				return null;
			}

			return variable;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Modifica una variable de tipo integer
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	
	public String updateVariableInt(Variable variable) {
		try {
			StringBuffer sql = new StringBuffer();

			String updateList = "max=" + variable.getMax();
			updateList = updateList + ",min=" + variable.getMin();
			updateList = updateList + ",nombrevariable='"
					+ variable.getNombrevariable() + "'";

			String tableList = TablasBD.TABLE_VARIABLES;

			String whereList = "idvariable=?";

			sql.append("UPDATE " + tableList + " SET " + updateList);
			sql.append(" WHERE  " + whereList);

			// select last_insert_id(); para conseguir el ultimo id insertado

			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdvariable() };

			types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
	/**
	 * Modifica una variable de tipo fecha
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableDate(Variable variable) {
		try {
			StringBuffer sql = new StringBuffer();

			String updateList = "fechadesde='" + variable.getFechadesde();
			updateList = updateList + "',fechahasta='"
					+ variable.getFechahasta();
			updateList = updateList + "',nombrevariable='"
					+ variable.getNombrevariable() + "'";

			String tableList = TablasBD.TABLE_VARIABLES;

			String whereList = "idvariable=?";

			sql.append("UPDATE " + tableList + " SET " + updateList);
			sql.append(" WHERE  " + whereList);


			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdvariable() };

			types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}
	
	/**
	 * Modifica una variable de tipo opción
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableOption(Variable variable) {
		try {
			StringBuffer sql = new StringBuffer();
			// Elimina todas las opciones de esa variable
			deleteOpciones(variable.getIdvariable());
			// Crea de nuevo todas las opciones de esa variable
			for (int i = 0; i < variable.getOpciones().size(); i++) {

				insertOpcion(variable.getIdvariable(), variable.getOpciones()
						.get(i).getNombreopcion());
			}

			// Modificamos el valor de multiopcion y nombre
			String updateList = "multiopcion=" + variable.getMultiopcion()
					+ ",nombrevariable='" + variable.getNombrevariable() + "'";
			String tableList = TablasBD.TABLE_VARIABLES;
			String whereList = "idvariable=?";

			sql.append("UPDATE " + tableList + " SET " + updateList);
			sql.append(" WHERE  " + whereList);

			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdvariable() };

			types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Modifica una variable de tipo decimal
	 * 
	 * @param variable Objeto de tipo Variable que contienen nuevos datos de la variable
	 * @return Devuelve 1 en caso de éxito y -1 en caso contario
	 */
	public String updateVariableDecimal(Variable variable) {
		try {
			StringBuffer sql = new StringBuffer();

			String updateList = "max=" + variable.getMax();
			updateList = updateList + ",min=" + variable.getMin();
			updateList = updateList + ",nombrevariable='"
					+ variable.getNombrevariable() + "'";

			String tableList = TablasBD.TABLE_VARIABLES;

			String whereList = "idvariable=?";

			sql.append("UPDATE " + tableList + " SET " + updateList);
			sql.append(" WHERE  " + whereList);

			// select last_insert_id(); para conseguir el ultimo id insertado

			Object[] parametros;
			int[] types;

			parametros = new Object[] { variable.getIdvariable() };

			types = new int[] { Types.INTEGER };

			if (jdbcTemplate.update(sql.toString(), parametros, types) == 1) {
				return "1";
			} else {
				return "-1";
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Métodpo que sirve para duplicar las variable de una aplicación en otra
	 * 
	 * @param idApplicationVieja Identificador de la aplicación de la que se quieren copiar las variables
	 * @param idApplicationNueva Identificador de la aplicación de la que se quieren pegar las variables
	 * @return
	 */
	public String duplicarVariables(Integer idApplicationVieja,
			Integer idApplicationNueva)

	{

		try {
			List<Variable> listaVariables = getVariables(idApplicationVieja);

			Iterator iteratorVariables = listaVariables.iterator();
			while (iteratorVariables.hasNext()) {
				Variable variable = (Variable) iteratorVariables.next();
				variable.setIdaplicacion(idApplicationNueva);

				int tipoVariable = variable.getIdtipo();
				switch (tipoVariable) {
				// es integer
				case 1:
					createVariableInt(variable);
					break;
				// es opcion
				case 2:
					List<Opcion> opciones = getOpciones(variable
							.getIdvariable());

					Iterator iterOpciones = opciones.iterator();
					String opcionesString = "";
					while (iterOpciones.hasNext()) {
						
						Opcion opcionAux = (Opcion) iterOpciones.next();
						
						opcionesString = opcionesString + opcionAux.getNombreopcion() + ";";
								
					}
					createVariableOpciones(variable, opcionesString);
					break;
				// es date
				case 3:
					createVariableFecha(variable);
					break;
				// es decimal
				case 4:
					createVariableDecimal(variable);
					break;

				default:
					break;
				}

			}
			return "1";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "-1";
		}

	}

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private AppTrackUtils appTrackUtils;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
