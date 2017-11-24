function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.countqueued1minuteaverage.count;
    var output = {
        suffix: "avg",
        units: "count",
        component: "countqueued1minuteaverage",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}




