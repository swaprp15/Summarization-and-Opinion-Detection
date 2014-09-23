var myModule = angular.module('myModule', ['ngRoute']);

myModule.service('CategoriesService', ['$http', function($http) {
	this.getCategories = function() {
		return $http({method: 'GET', url: 'http://localhost:9998/categories'})
	};
}]);

myModule.service('productSummaryService', ['$http', function($http) {
	this.getProductSummary = function(productId) {
		return $http({method: 'GET', url: 'http://localhost:9998/productsummary/' + productId})
	};
}]);


myModule.service('reviewService', ['$http', function($http) {
	this.getReviewText = function(productId, reviewId) {
		return $http({method: 'GET', url: 'http://localhost:9998/review/' + productId + "/" + reviewId})
	};
}]);


myModule.service('productsInfoPerCategory', ['$http', function($http) {
	this.getProductsInfoForCategory = function(category) {
		return $http({method: 'GET', url: 'http://localhost:9998/products/' + category})
	};
}]);


myModule.controller('productSummaryController', ['$scope', 'productsInfoPerCategory', 
															'productSummaryService', 
															'reviewService', 
                              'CategoriesService',
															function($scope, productsInfoPerCategory, 
                                               productSummaryService, 
                                               reviewService, 
                                               CategoriesService) {	
  
   $scope.productSummaries = [];


   $scope.showCompleteReview = function(productId, reviewId) {
   		reviewService.getReviewText(productId, reviewId).success(function(data, status, headers, config) {
    																	$scope.currentReview = data;
    								 							}).
    								 							error(function(data, status, headers, config) {
    																	alert(headers)
    								 							});
   };

   $scope.drawOverallSentimentChart = function(chartelementId, postivescore, negativescore) {
        google.load("visualization", "1", {packages:["corechart"], "callback": drawPieChart});

      function drawPieChart() {
        var data = google.visualization.arrayToDataTable([
          ['Sentiment', 'score'],
          ['Positive',     postivescore],
          ['Negative',     negativescore],
        ]);

        var options = {
          title: 'Sentiment',
          is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById(chartelementId));
        chart.draw(data, options);
      }
    };


   $scope.drawProdFeaturesChart = function(chartelementId) {

      google.load("visualization", "1", {packages:["corechart"], "callback": drawBarChart});

      function drawBarChart() {

      	var dataTable = new Array();

      	var headers = new Array();
      	headers.push('Feature');
      	headers.push('Positive');
      	headers.push('Negative');

      	dataTable.push(headers);

      	var featureSummaries = $scope.currentClickedProductSummary.featureSummaries;

      	for(var i=0; i < featureSummaries.length; i++) {
      		var featureArray = new Array();
      		featureArray.push(featureSummaries[i].featureName);
      		featureArray.push(featureSummaries[i].positiveCount);
      		featureArray.push(featureSummaries[i].negtiveCount);
      		dataTable.push(featureArray);
      	}

        var data = google.visualization.arrayToDataTable(dataTable);

        var options = {
          title: 'Feature Summary',
          vAxis: {title: '',  titleTextStyle: {color: 'red'}},
          height:400,
          width:400,
        };

        var chart = new google.visualization.BarChart(document.getElementById(chartelementId));
        chart.draw(data, options);
      }
    }


   $scope.getNumber = function(num) {
        return new Array(num);   
    }

    $scope.featureSummaryClicked = function(productId) {
    	 
    		for(var i=0; i < $scope.productSummaries.length; i++) {
    			if($scope.productSummaries[i].productId == productId) {
    				$scope.currentClickedProductSummary = $scope.productSummaries[i];
    				break;
    			}
    		}
    		
    }


    $scope.getProductsInfoForCategory = function(category) {
      $scope.productSummaries = [];
      productsInfoPerCategory.getProductsInfoForCategory(category)
                    .success(function(data, status, headers, config) {
                      $scope.productsInfo = data;
                      for (var i=0; i < $scope.productsInfo.length; i++) {
                        productSummaryService.getProductSummary($scope.productsInfo[i].productId)
                        .success(function(data, status, headers, config) {
                            $scope.productSummaries.push(data);            
                            $scope.drawOverallSentimentChart(data.productId, data.totalPostiveCount, data.totalNegativeCount);                  
                         }).
                            error(function(data, status, headers, config) {
                              alert(headers)
                            });
                      }
                     }).
                     error(function(data, status, headers, config) {
                        alert(headers)
                     });
        
      }

  
  CategoriesService.getCategories().success(function(data, status, headers, config) {
                      $scope.categories = data;
                      $scope.getProductsInfoForCategory(data[0])
                     }).
                     error(function(data, status, headers, config) {
                        alert(headers)
                     });    
    }]);
