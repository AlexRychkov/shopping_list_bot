import React from 'react';
import ApiClient, {MarkEnum, PeriodEnum} from "../../lib/api-client";

class Dashboard extends React.Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  componentDidMount() {
    const token = localStorage.getItem('token')
    const apiClient = new ApiClient();
    apiClient.createdStatistic(token, PeriodEnum.DAY).then(res =>
      this.setState({
        day: res.data.length,
      })
    )
    apiClient.createdStatistic(token, PeriodEnum.WEEK).then(res =>
      this.setState({
        week: res.data.length,
      })
    )
    apiClient.createdStatistic(token, PeriodEnum.MONTH).then(res =>
      this.setState({
        month: res.data.length,
      })
    )
    apiClient.createdStatistic(token, PeriodEnum.YEAR).then(res =>
      this.setState({
        year: res.data.length,
      })
    )
    apiClient.createdStatistic(token, PeriodEnum.ALL_TIME).then(res =>
      this.setState({
        all_time: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.DAY, MarkEnum.WAIT).then(res =>
      this.setState({
        wait_day: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.WEEK, MarkEnum.WAIT).then(res =>
      this.setState({
        wait_week: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.MONTH, MarkEnum.WAIT).then(res =>
      this.setState({
        wait_month: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.YEAR, MarkEnum.WAIT).then(res =>
      this.setState({
        wait_year: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.ALL_TIME, MarkEnum.WAIT).then(res =>
      this.setState({
        wait_all_time: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.DAY, MarkEnum.ABSENT).then(res =>
      this.setState({
        absent_day: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.WEEK, MarkEnum.ABSENT).then(res =>
      this.setState({
        absent_week: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.MONTH, MarkEnum.ABSENT).then(res =>
      this.setState({
        absent_month: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.YEAR, MarkEnum.ABSENT).then(res =>
      this.setState({
        absent_year: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.ALL_TIME, MarkEnum.ABSENT).then(res =>
      this.setState({
        absent_all_time: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.DAY, MarkEnum.BOUGHT).then(res =>
      this.setState({
        bought_day: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.WEEK, MarkEnum.BOUGHT).then(res =>
      this.setState({
        bought_week: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.MONTH, MarkEnum.BOUGHT).then(res =>
      this.setState({
        bought_month: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.YEAR, MarkEnum.BOUGHT).then(res =>
      this.setState({
        bought_year: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.ALL_TIME, MarkEnum.BOUGHT).then(res =>
      this.setState({
        bought_all_time: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.DAY, MarkEnum.CANCELED).then(res =>
      this.setState({
        canceled_day: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.WEEK, MarkEnum.CANCELED).then(res =>
      this.setState({
        canceled_week: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.MONTH, MarkEnum.CANCELED).then(res =>
      this.setState({
        canceled_month: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.YEAR, MarkEnum.CANCELED).then(res =>
      this.setState({
        canceled_year: res.data.length,
      })
    )
    apiClient.markStatistic(token, PeriodEnum.ALL_TIME, MarkEnum.CANCELED).then(res =>
      this.setState({
        canceled_all_time: res.data.length,
      })
    )
  }

  render() {
    return (
      <div className="container">
        <table>
          <tr>
            <td>
              <p>Created</p>
              <ul>
                <li>Day: {this.state.day}</li>
                <li>Week: {this.state.week}</li>
                <li>Month: {this.state.month}</li>
                <li>Year: {this.state.year}</li>
                <li>All time: {this.state.all_time}</li>
              </ul>
            </td>
            <td/>
            <td>
              <p>Wait ⏳ </p>
              <ul>
                <li>Day: {this.state.wait_day}</li>
                <li>Week: {this.state.wait_week}</li>
                <li>Month: {this.state.wait_month}</li>
                <li>Year: {this.state.wait_year}</li>
                <li>All time: {this.state.wait_all_time}</li>
              </ul>
            </td>
            <td/>
            <td>
              <p>Absent 🤷 </p>
              <ul>
                <li>Day: {this.state.absent_day}</li>
                <li>Week: {this.state.absent_week}</li>
                <li>Month: {this.state.absent_month}</li>
                <li>Year: {this.state.absent_year}</li>
                <li>All time: {this.state.absent_all_time}</li>
              </ul>
            </td>
          </tr>
          <tr>
            <td/>
            <td>
              <p>Bought ✅ </p>
              <ul>
                <li>Day: {this.state.bought_day}</li>
                <li>Week: {this.state.bought_week}</li>
                <li>Month: {this.state.bought_month}</li>
                <li>Year: {this.state.bought_year}</li>
                <li>All time: {this.state.bought_all_time}</li>
              </ul>
            </td>
            <td/>
            <td>
              <p>Canceled ❌ </p>
              <ul>
                <li>Day: {this.state.canceled_day}</li>
                <li>Week: {this.state.canceled_week}</li>
                <li>Month: {this.state.canceled_month}</li>
                <li>Year: {this.state.canceled_year}</li>
                <li>All time: {this.state.canceled_all_time}</li>
              </ul>
            </td>
          </tr>
        </table>
      </div>
    );
  }
}

export default Dashboard;