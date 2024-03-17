<!DOCTYPE>
<html>
<style>
    table,
    th,
    td {
        border: 1px solid black;
    }
</style>

<head>

    <head>

    <body>
        <h1>Welcome</h1>
        <table style="width:100%">
            <tr>
                <th>Id</th>
                <th>Current date</th>
                <th>Start Date</th>
                <th>Start Price</th>
                <th>Stop Loss Price</th>
                <th>Target Date</th>
                <th>Target Price</th>
                <th>Stock Id</th>
            </tr>
            <#list recommendation as recom>

                <tr>
                    <td>${recom.id!}</td>
                    <td> ${recom.currDate!}

                    </td>
                    <td>

                        ${recom.startDate!}
                    </td>
                    <td>

                        ${recom.startPrice!}
                    </td>
                    <td>

                        ${recom.stopLossPrice!}
                    </td>
                    <td>
                        ${recom.targetDate!}
                    </td>
                    <td>
                        ${recom.targetPrice!}
                    </td>
                    <td>
                        ${recom.stock.id!}
                    </td>
                </tr>
            </#list>
        </table><br>
        <table style="width:100%">
            <tr>
                <th>Id</th>
                <th>Symbol</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
            </tr>
            <#list stocks as stocks>
                <tr>
                    <td>${stocks.id!}
                    </td>
                    <td>
                        ${stocks.symbol!}
                    </td>
                    <td>
                        ${stocks.name!}
                    </td>
                    <td>
                        ${stocks.description!}
                    </td>
                    <td>
                        ${stocks.price!}
                    </td>
                </tr>
            </#list>
        </table>
    </body>

</html>