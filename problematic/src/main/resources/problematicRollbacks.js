function apply(input) {
    var metric = JSON.parse(input);
    var value = metric.extraProperties.entity.rolledbackcount.count;
    var output = {
        suffix: "count",
        units: "total",
        component: "rollbacks",
        application: "problematic",
        value: value
    };
    return JSON.stringify(output);
}


