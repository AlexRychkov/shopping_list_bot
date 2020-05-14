import ApiClient from '../../lib/api-client'
import Link from 'next/link'
import redirect from "../../lib/redirect"

function Login() {
  return (
    <>
      <p>You're not logged in. Use <Link href="/">main page</Link> for instructions.</p>
    </>
  )
}

export default Login

export async function getServerSideProps({token, res}) {
  const apiClient = new ApiClient();
  const isLoggedIn = await apiClient.login(token)
  if (isLoggedIn){
    redirect(res, '/dashboard')
  }
  return {
    props: {
      loggedIn: isLoggedIn
    }
  }
}