import ApiClient, {INVALID_TOKEN} from '../../lib/api-client'
import {useRouter} from 'next/router'

const Login = ({loggedIn, token}) => {
  if (token && typeof document !== "undefined") {
    localStorage.setItem('token', token)
    const router = useRouter()
    router.push('/dashboard')
  }

  return (
    <>
      {loggedIn && <p>You're not logged in. Use <a href="/">main page</a> for instructions.</p>}
      {!loggedIn && <p>You're logged in. Go to <a href="/dashboard">dashboard</a>.</p>}
    </>
  )
}

export default Login

export async function getServerSideProps({params}) {
  const apiClient = new ApiClient();
  const exchangedToken = await apiClient.exchToken(params.token)
  if (exchangedToken !== INVALID_TOKEN) {
    return {
      props: {
        token: exchangedToken
      }
    }
  } else return {
    props: {
      loggedIn: !!exchangedToken
    }
  }
}