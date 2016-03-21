package am.opensource.LiftwebHelpers.Util

import net.liftweb.http.S

/**
  * Created by silvs on 4/03/2016.
  */
object Application {

	var failoverBaseURI: String = "http://localhost:8080"

	val HostPortAnd: String ⇒ String = path ⇒ S.containerRequest.map(r => (r.scheme, r.serverPort) match {
		case ("http", 80) if r.header("X-SSL").isDefined => "https://" + r.serverName + path
		case ("http", 80) => "http://" + r.serverName + path
		case ("https", 443) => "https://" + r.serverName + path
		case (sch, port) => sch + "://" + r.serverName + ":" + port + path
	}) openOr failoverBaseURI
}
