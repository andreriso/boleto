package cursoJava;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Path("/boleto")
public class Boleto {

    @GET
    @Path("/{valor}/{diaVenc}/{mesVenc}/{anoVenc}/{diaPagto}/{mesPagto}/{anoPagto}")
    @Produces(MediaType.TEXT_PLAIN)
	public String emitir(
	  @PathParam("valor") String valor,
	  @PathParam("diaVenc") String diaVenc,  
	  @PathParam("mesVenc") String mesVenc, 
	  @PathParam("anoVenc") String anoVenc, 
	  @PathParam("diaPagto") String diaPagto, 
	  @PathParam("mesPagto") String mesPagto, 
	  @PathParam("anoPagto") String anoPagto) {

		String dataVencimento = mesVenc + "/" + diaVenc + "/" + anoVenc;
		String dataPagamento = mesPagto + "/" + diaPagto + "/" + anoPagto;

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		try {
			Date venc = sdf.parse(dataVencimento);
			Date pagto = sdf.parse(dataPagamento);

			long dt = (pagto.getTime() - venc.getTime()) + 3600000;
			long dias = (dt / 86400000L);
			return String.valueOf(Double.valueOf(valor) * 0.01 * dias);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Erro na emissão";

	}
}