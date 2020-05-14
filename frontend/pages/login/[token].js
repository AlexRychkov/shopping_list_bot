import ApiClient, {INVALID_TOKEN} from '../../lib/api-client'
import redirect from "../../lib/redirect"

function Login({loggedIn}) {
  return (
    <>
      { loggedIn && <p>You're not logged in. Use <a href="/">main page</a> for instructions.</p> }
      { !loggedIn && <p>You're logged in. Go to <a href="/dashboard">dashboard</a>.</p> }
    </>
  )
}

export default Login

export async function getServerSideProps({params, res}) {
  const apiClient = new ApiClient();
  const exchangedToken = await apiClient.exchToken(params.token)
  if (exchangedToken !== INVALID_TOKEN){
    // TODO localStorage set authorization
    redirect(res, '/dashboard')
  } else return {
    props: {
      loggedIn: !!exchangedToken
    }
  }
}