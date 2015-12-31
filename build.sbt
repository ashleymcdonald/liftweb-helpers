name := "LiftwebHelpers"

version := "0.0.1"

organization := "am.opensource"

scalaVersion := "2.11.2"

resolvers ++= Seq(
	"snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
    "releases"        at "https://oss.sonatype.org/content/repositories/releases"
)

// scalacOptions ++= Seq("-deprecation", "-unchecked")
scalacOptions := Seq("-optimise","-unchecked","-deprecation","-Ydead-code","-Yinline-handlers","-Yinline-warnings","-Ybackend:GenBCode","-Ydelambdafy:method","-target:jvm-1.7","-explaintypes","-feature","-Xlint","-Dscalac.patmat.analysisBudget=512","-Ydebug" ,"-Ylog:refchecks") // some moreso evil flags to make sure code is clean

javacOptions ++= Seq("-Xlint:unchecked","-target","1.8") // target environment is java 8 or later only

// Add lift library Shiznit
libraryDependencies ++= {
	val liftVersion = "2.6.+"
	Seq(
		"net.liftweb" %% "lift-webkit" % liftVersion,       // lift libraries
		"net.liftweb" %% "lift-util" % liftVersion,
		"net.liftweb" %% "lift-common" % liftVersion,
		"net.liftweb" %% "lift-json" % liftVersion,
		"net.liftweb" %% "lift-mapper" % liftVersion        // db orm
	)
}

// DATE AND TIME HELPERS
libraryDependencies ++= Seq(
	//"joda-time" %% "joda-time" % "2.5", // Joda Time
	"com.github.nscala-time" %% "nscala-time" % "1.4.0", // new joda time for scala
	"org.ocpsoft.prettytime" % "prettytime" % "2.+" // prettytime library (for making "3 days ago" etc.)
)

// APACHE Commons for helpers etc.
libraryDependencies ++= Seq(
	"commons-io" % "commons-io" % "[2.0,3.0[", // helpers to save time
	"commons-lang" % "commons-lang" % "[2.0,3.99[", // helpers to save time
	"commons-pool" % "commons-pool" % "[1.6,2.0[" // used by jms pool
)