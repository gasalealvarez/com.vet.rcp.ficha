package com.vet.rcp.ficha;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_OPEN_PARAMETRO = "com.vet.rcp.ficha.open_CreateParameters";
    public static final String CMD_OPEN_RESULTADO = "com.vet.rcp.ficha.Open_SelectionDialog";
    public static final String CMD_OPEN_PROPIETARIO = "com.vet.rcp.ficha.Open_NuevoPropietario";
    public static final String CMD_OPEN_PELAJE = "com.vet.rcp.ficha.open_NuevoPelaje";
	public static final String CMD_OPEN_ESPECIE = "com.vet.rcp.ficha.open_NuevaEspecie";
	public static final String CMD_OPEN_PACIENTE = "com.vet.rcp.ficha.open_NuevoPaciente";
	public static final String CMD_OPEN_PANEL = "com.vet.rcp.ficha.open_NuevoPanel";
	public static final String CMD_OPEN_SERIE = "com.vet.rcp.ficha.open_Serie";
	public static final String CMD_OPEN_SANGRE = "com.vet.rcp.ficha.OpenViewSangre";
	public static final String CMD_OPEN_PLAN = "com.vet.rcp.ficha.openViewPlan";
	public static final String CMD_OPEN_FOTOS = "com.vet.rcp.ficha.OpenViewFotos";
	public static final String CMD_OPEN_CASOS = "com.vet.rcp.ficha.OpenViewAction";
}
