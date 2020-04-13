object ScalacOptions {
  val options = Seq(
    "-Werror",
    "-Wdead-code",
    "-Wextra-implicit",
    "-Wnumeric-widen",
    "-Woctal-literal",
    "-Wself-implicit",
    "-Wunused",
    "-Wvalue-discard",
    "-Xlint",
    "-Xfatal-warnings",

    "-unchecked",
    "-feature",
    "-deprecation:false"
  )
}