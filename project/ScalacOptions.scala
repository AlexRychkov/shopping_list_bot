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

    "-unchecked",
    "-feature",
    "-deprecation:false"
  )
}
