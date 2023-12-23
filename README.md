<h1> Overview </h1>
This application is developed to analyze the duration of collaboration between pairs of employees on common projects. It takes input data from a CSV file containing information about employees (EmpID), projects (ProjectID), and the time period they worked together (DateFrom, DateTo). The primary objective is to identify pairs of employees who have worked together for the longest period and display the duration for each common project.

<h2> Algorithm Overview</h2>
<h2>Data Loading from CSV</h2>

<p>
    To load data from the provided CSV file:
    <ol>
        <li>Read the input data from the CSV file.</li>
        <li>Parse the CSV data and convert it into a suitable data structure.</li>
    </ol>
</p>

<h2>Date Handling</h2>

<p>
    To handle various date formats and ensure consistency:
    <ol>
        <li>Accommodate different ways dates may be represented.</li>
        <li>Convert NULL values in DateTo to the current date for consistency.</li>
    </ol>
</p>

<h2>Collaboration Duration Calculation</h2>

<p>
    To calculate the duration of collaboration:
    <ol>
        <li>Iterate through the dataset to identify pairs of employees working on the same project.</li>
        <li>Calculate the duration of collaboration for each pair on each project.</li>
    </ol>
</p>

<h2>Identify Longest Collaboration</h2>

<p>
    To identify the longest collaboration:
    <ol>
        <li>Keep track of the longest collaboration duration for each pair.</li>
        <li>Store information about the corresponding project for the longest collaboration.</li>
    </ol>
</p>

<h2>Output Results</h2>

<p>
    To display the results:
    <ol>
        <li>Display the pairs of employees who have worked together for the longest period.</li>
        <li>Include the duration of collaboration for each common project.</li>
    </ol>
</p>

</body>

</html>
